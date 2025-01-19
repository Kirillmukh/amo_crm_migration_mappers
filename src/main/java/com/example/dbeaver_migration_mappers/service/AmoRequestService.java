package com.example.dbeaver_migration_mappers.service;

import com.example.dbeaver_migration_mappers.client.AmoCRMRestClient;
import com.example.dbeaver_migration_mappers.client.AmoCRMSourceRestClient;
import com.example.dbeaver_migration_mappers.crm_models.embedded.EmbeddedLead;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMContact;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMLead;
import com.example.dbeaver_migration_mappers.crm_models.entity.wrapper.CRMCompanyCRMContactsListWrapper;
import com.example.dbeaver_migration_mappers.crm_models.entity.wrapper.CRMCompanyCRMContactsWrapper;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMCompanyRequest;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMContactRequest;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMLeadRequest;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMToEntityRequest;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMCompanyResponse;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMComplexLeadResponse;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMContactResponse;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMToEntityResponse;
import com.example.dbeaver_migration_mappers.mapper.ToEntityRequestMapper;
import com.example.dbeaver_migration_mappers.util.file.exception.FileReadingException;
import com.example.dbeaver_migration_mappers.util.file.exception.FileWritingException;
import com.example.dbeaver_migration_mappers.util.keeper.CompaniesKeeper;
import com.example.dbeaver_migration_mappers.util.keeper.ContactsKeeper;
import com.example.dbeaver_migration_mappers.util.keeper.IdsKeeper;
import com.example.dbeaver_migration_mappers.util.keeper.LeadsKeeper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

// TODO: 04.01.2025 Сделать паузу между запросами

@Service
@RequiredArgsConstructor
@Slf4j
public class AmoRequestService implements RequestService {
    private final AmoCRMRestClient amoCRMRestClient;
    private final ContactsKeeper contactsKeeper;
    private final CompaniesKeeper companiesKeeper;
    private final LeadsKeeper leadsKeeper;
    private final ToEntityRequestMapper toEntityRequestMapper;
    private final AmoCRMSourceRestClient amoCRMSourceRestClient;
    @Value("${config.crm.request.limit}")
    private int requestLimit;
    @Value("${config.crm.request.timeout_seconds}")
    private int requestTimeout;

    @Override
    public void saveComplexLead(CRMLeadRequest crmLeadRequest) {
        int requestLength = crmLeadRequest.crmLead().size();
        List<List<CRMContact>> contactsQueue = new ArrayList<>(requestLength);
        log.info("crmLead size = {}", crmLeadRequest.crmLead().size());
        for (CRMLead crmLead : crmLeadRequest.crmLead()) {
            EmbeddedLead embeddedLead = crmLead.getEmbeddedLead();
            if (embeddedLead.getContacts() != null && embeddedLead.getContacts().size() > 1) {
                contactsQueue.add(embeddedLead.getContacts());
                embeddedLead.setContacts(null);
            } else {
                contactsQueue.add(null);
            }
        }
        log.info("contactsQueue size = {}", contactsQueue.size());

        List<CRMLeadRequest> groupedComplexLead = group(crmLeadRequest.crmLead()).stream()
                .map(CRMLeadRequest::new)
                .toList();
        log.info("groupedComplexLead size = {}", groupedComplexLead.stream().map(CRMLeadRequest::crmLead).mapToLong(Collection::size).sum());

        List<List<CRMContactRequest>> groupedContacts = contactsQueue.stream()
                .map(this::group)
                .map(lists -> {
                    if (lists == null) return null;
                    return lists.stream()
                            .map(CRMContactRequest::new)
                            .collect(Collectors.toList());
                })
                .toList();
        log.info("groupedContacts default size = {}", groupedContacts.size());

        // lead request
        List<CRMComplexLeadResponse> complexLeadResponses = new ArrayList<>();
        for (CRMLeadRequest leadRequest : groupedComplexLead) {
            List<CRMComplexLeadResponse> complexLead = amoCRMRestClient.createComplexLead(leadRequest);
            try {
                Thread.sleep(requestTimeout * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            complexLead.stream()
                    .peek(this::keep)
                    .forEach(complexLeadResponses::add);
        }
//        List<CRMComplexLeadResponse> complexLeadResponses = groupedComplexLead.stream()
//                .map(amoCRMRestClient::createComplexLead)
//                .flatMap(Collection::stream)
//                .peek(this::keep)
//                .toList();
        log.info("complexLeadResponses size = {}", complexLeadResponses.size());

        if (complexLeadResponses.size() != groupedContacts.size()) {
            String message = "Different length in groupedComplexLead and groupedContacts";
            log.error(message);
            throw new IllegalStateException(message);
        }

        // contact request and link it with lead
        List<CRMToEntityResponse> crmToEntityResponses = new ArrayList<>();
        for (int i = 0; i < groupedContacts.size(); i++) {
            if (groupedContacts.get(i) == null) {
                continue;
            }
            CRMComplexLeadResponse crmComplexLeadResponse = complexLeadResponses.get(i);
            for (CRMContactRequest contactRequest : groupedContacts.get(i)) {
                List<CRMContactResponse.Embedded.Contact> contacts = amoCRMRestClient.createContact(contactRequest).embedded().contacts();
                try {
                    Thread.sleep(requestTimeout * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                contacts.forEach(this::keep);
                CRMToEntityRequest crmToEntityRequest = new CRMToEntityRequest(toEntityRequestMapper.mapContactsToLinks(contacts));
                CRMToEntityResponse crmToEntityResponse = amoCRMRestClient.linkLead(crmComplexLeadResponse.id(), crmToEntityRequest);
                try {
                    Thread.sleep(requestTimeout * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                crmToEntityResponses.add(crmToEntityResponse);
            }
//            List<CRMToEntityResponse> crmToEntityResponses = groupedContacts.get(i).stream()
//                    .map(amoCRMRestClient::createContact)
//                    .map(companies -> companies.embedded().companies())
//                    .peek(this::keep)
//                    .map(toEntityRequestMapper::mapContactsToLeadLinks)
//                    .map(CRMToEntityRequest::new)
//                    .map(toEntity -> amoCRMRestClient.linkLead(crmComplexLeadResponse.id(), toEntity))
//                    .toList();
        }
        log.info("crmToEntityResponses: {}", crmToEntityResponses.stream().map(response -> response.embedded().links()).toList());
        try {
            this.leadsKeeper.append();
        } catch (FileWritingException e) {
            log.error("Exception {} when leadsKeeper.append()", e.getMessage());
        }
        try {
            this.contactsKeeper.append();
        } catch (FileWritingException e) {
            log.error("Exception {} when contactsKeeper.append()", e.getMessage());
        }
        try {
            this.companiesKeeper.append();
        } catch (FileWritingException e) {
            log.error("Exception {} when companiesKeeper.append()", e.getMessage());
        }

        /// split request by 40 entities

        // row              -> /leads/complex
        // companies         -> /companies
        /// split companies by 40 entities
        // link companies    -> /leads/link
    }


    @Override
    public void saveContact(CRMContactRequest crmContactRequest) {
        List<CRMContact> crmContacts = crmContactRequest.crmContactList();
        List<List<CRMContact>> group = this.group(crmContacts);
        for (List<CRMContact> list : group) {
            CRMContactRequest request = new CRMContactRequest(list);
            List<CRMContactResponse.Embedded.Contact> contacts = amoCRMRestClient.createContact(request).embedded().contacts();
            try {
                Thread.sleep(requestTimeout * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            contacts.forEach(this::keep);
        }
        try {
            this.contactsKeeper.append();
        } catch (FileWritingException e) {
            log.error("Exception {} when contactsKeeper.append()", e.getMessage());
        }

        /// split request and save
    }

    @Override
    public void saveCompanyAndContacts(CRMCompanyCRMContactsListWrapper crmCompanyRequest) {
        int size = crmCompanyRequest.list().size();
        List<List<List<CRMContact>>> groupedContacts = crmCompanyRequest.list().stream()
                .map(CRMCompanyCRMContactsWrapper::crmContact)
                .map(this::group)
                .toList();
        List<CRMCompany> companiesQueue = crmCompanyRequest.list().stream().map(CRMCompanyCRMContactsWrapper::crmCompany).toList();
        List<CRMCompanyRequest> groupedCompanies = group(companiesQueue).stream().map(CRMCompanyRequest::new).toList();

        // load companies
        List<CRMCompanyResponse.Embedded.Company> companiesResponses = new ArrayList<>();
        for (CRMCompanyRequest companyRequest : groupedCompanies) {
            amoCRMRestClient.createCompany(companyRequest).embedded().companies().stream()
                    .peek(this::keep)
                    .forEach(companiesResponses::add);
        }

        if (companiesResponses.size() != groupedContacts.size()) {
            String message = "Different length in companiesResponses and groupedContacts";
            log.error(message);
            log.error("companiesResponses.size() = {}", companiesResponses.size());
            log.error("groupedContacts.size() = {}", groupedContacts.size());
            throw new IllegalStateException(message);
        }

        // load contacts and link it with companies
        List<CRMToEntityResponse> crmToEntityResponses = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            CRMCompanyResponse.Embedded.Company company = companiesResponses.get(i);
            List<List<CRMContact>> list = groupedContacts.get(i);
            for (List<CRMContact> crmContacts : list) {
                List<CRMContactResponse.Embedded.Contact> contacts = amoCRMRestClient.createContact(new CRMContactRequest(crmContacts)).embedded().contacts();
                try {
                    Thread.sleep(requestTimeout * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                contacts.forEach(this::keep);
                CRMToEntityRequest crmToEntityRequest = new CRMToEntityRequest(toEntityRequestMapper.mapContactsToLinks(contacts));
                CRMToEntityResponse crmToEntityResponse = amoCRMRestClient.linkCompany(company.id(), crmToEntityRequest);
                try {
                    Thread.sleep(requestTimeout * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                crmToEntityResponses.add(crmToEntityResponse);
            }
        }
        log.info("crmToEntityResponses: {}", crmToEntityResponses.stream().map(response -> response.embedded().links()).toList());
        try {
            this.contactsKeeper.append();
        } catch (FileWritingException e) {
            log.error("Exception {} when contactsKeeper.append()", e.getMessage());
        }
        try {
            this.companiesKeeper.append();
        } catch (FileWritingException e) {
            log.error("Exception {} when companiesKeeper.append()", e.getMessage());
        }
    }

    @Override
    public void deleteEntities() {
        deleteEntity(this.contactsKeeper, amoCRMSourceRestClient::deleteContact);
        deleteEntity(this.companiesKeeper, amoCRMSourceRestClient::deleteCompany);
        deleteEntity(this.leadsKeeper, amoCRMSourceRestClient::deleteLead);
    }
    private void deleteEntity(IdsKeeper idsKeeper, Consumer<String> crmDeleteRequest) {
        List<String> ids = null;
        try {
            idsKeeper.read();
            ids = new ArrayList<>(idsKeeper.getIds());
        } catch (FileReadingException e) {
            log.error("deleteEntity threw FileReadingException: {}", e.getMessage());
        }
        if (ids == null) {
            log.error("delete 0 entities by idsKeeper: {}", idsKeeper);
            return;
        }
        for (String id : ids) {
            if (id.isBlank()) continue;
            crmDeleteRequest.accept(id);
            try {
                Thread.sleep(requestTimeout * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            idsKeeper.delete(ids);
        } catch (FileReadingException | FileWritingException e) {
            log.error("deleteEntity threw FileReadingException | FileWritingException: {}", e.getMessage());
        }
    }

    private void keep(CRMContactResponse.Embedded.Contact contact) {
        if (contact == null) return;
        try {
            this.contactsKeeper.offer(String.valueOf(contact.id()));
        } catch (FileWritingException e) {
            log.error("error while writing contacts info");
        }
    }
    private void keep(CRMComplexLeadResponse crmComplexLeadResponse) {
        if (crmComplexLeadResponse.id() != null) {
            try {
                this.leadsKeeper.offer(String.valueOf(crmComplexLeadResponse.id()));
            } catch (FileWritingException e) {
                log.error("error while writing leads info");
            }
        }
        if (crmComplexLeadResponse.companyId() != null) {
            try {
                this.companiesKeeper.offer(String.valueOf(crmComplexLeadResponse.companyId()));
            } catch (FileWritingException e) {
                log.error("error while writing companies info");
            }
        }
        if (crmComplexLeadResponse.contactId() != null) {
            try {
                this.contactsKeeper.offer(String.valueOf(crmComplexLeadResponse.contactId()));
            } catch (FileWritingException e) {
                log.error("error while writing contacts info");
            }
        }
    }

    private void keep(CRMCompanyResponse.Embedded.Company company) {
        if (company == null) return;
        try {
            this.companiesKeeper.offer(String.valueOf(company.id()));
        } catch (FileWritingException e) {
            log.error("error while writing companies info");
        }
    }

    private <T> List<List<T>> group(List<T> elements) {
        if (elements == null) return null;

        List<List<T>> result = new ArrayList<>();

        int size = elements.size();
        for (int i = 0; i < size; i += requestLimit) {
            result.add(elements.subList(i, Math.min(i + requestLimit, size)));
        }
        return result;
    }
}