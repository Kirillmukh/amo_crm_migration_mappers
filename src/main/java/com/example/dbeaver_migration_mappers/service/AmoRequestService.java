package com.example.dbeaver_migration_mappers.service;

import com.example.dbeaver_migration_mappers.client.AmoCRMRestClient;
import com.example.dbeaver_migration_mappers.client.AmoCRMSourceRestClient;
import com.example.dbeaver_migration_mappers.crm_models.constants.CompanyFieldsID;
import com.example.dbeaver_migration_mappers.crm_models.embedded.EmbeddedLead;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMContact;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMLead;
import com.example.dbeaver_migration_mappers.crm_models.entity.wrapper.CRMCompanyCRMContactsListWrapper;
import com.example.dbeaver_migration_mappers.crm_models.entity.wrapper.CRMCompanyCRMContactsWrapper;
import com.example.dbeaver_migration_mappers.crm_models.entity.wrapper.InputCompaniesCRMContactsWrapper;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMCompanyRequest;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMContactRequest;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMLeadRequest;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMToEntityRequest;
import com.example.dbeaver_migration_mappers.crm_models.response.*;
import com.example.dbeaver_migration_mappers.input_models.InputCompany;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    @Value("${config.crm.request.timeout_millis}")
    private long requestTimeout;
    @Value("${config.crm.delete_request.timeout_millis}")
    private long deleteRequestTimeout;

    @Override
    public void saveComplexLead(CRMLeadRequest crmLeadRequest, int offset) {
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
        int groupedComplexLeadSize = groupedComplexLead.size();
        List<CRMComplexLeadResponse> complexLeadResponses = new ArrayList<>();
        try {
            for (; offset < groupedComplexLeadSize; offset++) {
                log.info("Part 1 / 2 request iteration: {} / {}; completeness {}%", offset, groupedComplexLeadSize, 100 * (offset + 1) / groupedComplexLeadSize);
                CRMLeadRequest leadRequest = groupedComplexLead.get(offset);
                List<CRMComplexLeadResponse> complexLead = amoCRMRestClient.createComplexLead(leadRequest);
                sleepRequestTimeout();
                complexLead.stream()
                        .peek(this::keep)
                        .forEach(complexLeadResponses::add);
            }
        } finally {
            try {
                this.leadsKeeper.append();
            } catch (FileWritingException e) {
                log.error("Exception {} when leadsKeeper.append()", e.getMessage());
            }
        }


        log.info("complexLeadResponses size = {}", complexLeadResponses.size());

        if (complexLeadResponses.size() != groupedContacts.size()) {
            String message = "Different length in groupedComplexLead and groupedContacts";
            log.error(message);
            throw new IllegalStateException(message);
        }

        try {
            leadsKeeper.read();
        } catch (FileReadingException e) {
            log.error("Exception {} when leadsKeeper.read()", e.getMessage());
            throw new IllegalStateException(e);
        }
        List<Integer> leadIds = leadsKeeper.getIds().stream().map(Integer::valueOf).toList();

        // contact request and link it with lead
        List<CRMToEntityResponse> crmToEntityResponses = new ArrayList<>();
        try {
            for (int i = offset - groupedComplexLeadSize; i < groupedContacts.size(); i++, offset++) {
                log.info("Part 2 / 2 request iteration: {} / {}; completeness {}%", offset, offset + groupedContacts.size(), (1 + offset) * 100 / (groupedContacts.size() + offset));
                if (groupedContacts.get(i) == null) {
                    continue;
                }

                for (CRMContactRequest contactRequest : groupedContacts.get(i)) {

                    List<CRMContactResponse.Embedded.Contact> contacts = amoCRMRestClient.createContact(contactRequest).embedded().contacts();
                    sleepRequestTimeout();
                    contacts.forEach(this::keep);

                    CRMToEntityRequest crmToEntityRequest = new CRMToEntityRequest(toEntityRequestMapper.mapContactsToLinks(contacts));
                    CRMToEntityResponse crmToEntityResponse = amoCRMRestClient.linkLead(leadIds.get(i), crmToEntityRequest);
                    sleepRequestTimeout();
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
        } finally {
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
    }
    @Override
    public void saveNewContacts(CRMContactRequest crmContactRequest, int offset) {
        try {
            List<CRMContact> crmContacts = crmContactRequest.crmContactList();
            List<CRMContactRequest> requests = this.group(crmContacts).stream().map(CRMContactRequest::new).toList();
            int size = requests.size();
            for (int i = offset; i < size; i++) {
                log.info("Request iteration: {} / {}; completeness {}%", i, size, (i + 1) * 100 / size);
                CRMContactRequest request = requests.get(i);
                log.info("Request to crm: createContact; body: {}", request);
                List<CRMContactResponse.Embedded.Contact> contacts = amoCRMRestClient.createContact(request).embedded().contacts();
                contacts.forEach(this::keep);
                sleepRequestTimeout();
            }
        } finally {
            try {
                this.contactsKeeper.append();
            } catch (FileWritingException e) {
                log.error("Exception {} when contactsKeeper.append()", e.getMessage());
            }
        }
    }
    @Override
    public void saveNewContactsOldCompanies(CRMCompanyCRMContactsListWrapper crmCompanyRequest, int offset) {
        try {
            int size = crmCompanyRequest.list().size();
            List<List<List<CRMContact>>> groupedContacts = crmCompanyRequest.list().stream()
                    .map(CRMCompanyCRMContactsWrapper::crmContact)
                    .map(this::group)
                    .toList();
            List<CRMCompany> companiesQueue = crmCompanyRequest.list().stream().map(CRMCompanyCRMContactsWrapper::crmCompany).toList();
            List<CRMCompanyRequest> groupedCompanies = group(companiesQueue).stream().map(CRMCompanyRequest::new).toList();

            // load companies
            List<CRMCompanyResponse.Embedded.Company> companiesResponses = new ArrayList<>();
            int companySize = groupedCompanies.size();
            for (; offset < companySize; offset++) {
                log.info("Part 1 / 2; request iteration: {} / {}; completeness {}%", offset, size, (1 + offset) * 100 / companySize);
                CRMCompanyRequest companyRequest = groupedCompanies.get(offset);
                log.info("Request to crm: createCompany; body: {}", companyRequest);
                amoCRMRestClient.createCompany(companyRequest).embedded().companies().stream()
                        .peek(this::keep)
                        .forEach(companiesResponses::add);
                sleepRequestTimeout();
            }
            try {
                this.companiesKeeper.append();
            } catch (FileWritingException e) {
                log.error("Exception {} when companiesKeeper.append()", e.getMessage());
            }

            if (companiesResponses.size() != groupedContacts.size()) {
                String message = "Different length in companiesResponses and groupedContacts";
                log.error(message);
                log.error("companiesResponses.size() = {}", companiesResponses.size());
                log.error("groupedContacts.size() = {}", groupedContacts.size());
                log.warn("maybe you should rollback data, because all companies fully uploaded to crm (check target/company_ids.txt)");
                throw new IllegalStateException(message);
            }

            try {
                companiesKeeper.read();
            } catch (FileReadingException e) {
                log.error("Exception {} when companiesKeeper.read()", e.getMessage());
                throw new IllegalStateException(e);
            }
            List<Integer> companiesIds = companiesKeeper.getIds().stream().map(Integer::valueOf).toList();


            // load contacts and link it with companies
            for (int i = offset - companySize; i < size; i++, offset++) {
                log.info("part 2 / 2 request iteration: {} / {}; completeness {}%", offset, size + offset, (offset + 1) * 100 / (size + offset));
                int companyId = companiesIds.get(i);
                List<List<CRMContact>> list = groupedContacts.get(i);
                for (List<CRMContact> crmContacts : list) {
                    log.info("Request to crm: createContact; body: {}", crmContacts);
                    List<CRMContactResponse.Embedded.Contact> contacts = amoCRMRestClient.createContact(new CRMContactRequest(crmContacts)).embedded().contacts();
                    log.info("Response from crm: createContact; body: {}", contacts);
                    sleepRequestTimeout();
                    contacts.forEach(this::keep);

                    CRMToEntityRequest crmToEntityRequest = new CRMToEntityRequest(toEntityRequestMapper.mapContactsToLinks(contacts));
                    log.info("Request to crm: linkContact to company; body: {}", crmToEntityRequest);
                    CRMToEntityResponse crmToEntityResponse = amoCRMRestClient.linkCompany(companyId, crmToEntityRequest);
                    log.info("Response from crm: linkContact to company; body: {}", crmToEntityResponse);
                    sleepRequestTimeout();
                }
            }
        } finally {
            try {
                this.contactsKeeper.append();
            } catch (FileWritingException e) {
                log.error("Exception {} when contactsKeeper.append()", e.getMessage());
            }
        }
    }
    @Override
    public void saveNewCompaniesNewContacts(InputCompaniesCRMContactsWrapper listWrapper, int offset) {
        int size = listWrapper.inputCompanies().size();
        List<InputCompany> inputCompanies = listWrapper.inputCompanies();

        List<List<List<CRMContact>>> groupedContacts = listWrapper.contacts().stream()
                .map(this::group)
                .toList();

        if (inputCompanies.size() != groupedContacts.size()) {
            log.error("inputCompanies.size() != groupedContacts.size()");
            throw new RuntimeException("inputCompanies.size() != groupedContacts.size()");
        }

        try {
            String hint = null;
            for (int i = offset; i < size; i++) {
                if (i == offset) {
                    File file = new File("hint.txt");
                    if (file.length() != 0) {
                        try {
                            hint = Files.readString(file.toPath());
                            new FileWriter(file, false).close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
                log.info("Request iteration: {} / {}; completeness: {}%", i, size, (i + 1) * 100 / size);
                InputCompany inputCompany = inputCompanies.get(i);
                List<List<CRMContact>> contactRequests = groupedContacts.get(i);

                // find companyId
                Integer companyId = null;
                List<CRMCompaniesResponse.Embedded.Company> companies = null;
                if (hint != null) {
                    companyId = Integer.valueOf(hint);
                } else {
                    log.info("Request to crm: findCompanyByName; body: {}", inputCompany);
                    companies = amoCRMRestClient.findCompanyByName(inputCompany.getName()).embedded().companies();

                    if (companies.isEmpty()) {
                        log.error("Empty response from CRM");
                        throw new RuntimeException("Empty response from CRM");
                    } else if (companies.size() > 1) {
                        log.info("Response from CRM consist of 2 or more companies");
                        String web = inputCompany.getWeb();
                        OUTER:
                        for (var company : companies) {
                            if (inputCompany.getName().equals(company.name())) {
                                companyId = company.id();
                                break;
                            }
                            for (var cfv : company.customFieldValues()) {
                                if (cfv.fieldId() == CompanyFieldsID.ALTERNATIVE_NAME && inputCompany.getName().equals(cfv.values().get(0).value())
                                        || cfv.fieldId() == CompanyFieldsID.WEB && web.equals(cfv.values().get(0).value())) {
                                    companyId = company.id();
                                    break OUTER;
                                }
                            }
                        }
                        if (companyId == null) {
                            log.error("Company not found: inputCompany = {}, responseCompanies = {}", inputCompany, companies);
                            throw new RuntimeException("Company not found");
                        }
                    } else {
                        companyId = companies.get(0).id();
                    }
                }
                log.info("CompanyId = {}", companyId);
                try {
                    companiesKeeper.offer(companyId.toString());
                } catch (FileWritingException e) {
                    log.error("error while writing companies info");
                    log.error("error when writing id = {} to companiesKeeper", companyId);
                }
                sleepRequestTimeout();

                // load contacts and link it with companies
                for (List<CRMContact> crmContacts : contactRequests) {
                    log.info("Request to crm: createContact; body: {}", crmContacts);
                    List<CRMContactResponse.Embedded.Contact> contacts = amoCRMRestClient.createContact(new CRMContactRequest(crmContacts)).embedded().contacts();
                    log.info("Response from crm: createContact; body: {}", contacts);
                    contacts.forEach(this::keep);
                    sleepRequestTimeout();

                    CRMToEntityRequest crmToEntityRequest = new CRMToEntityRequest(toEntityRequestMapper.mapContactsToLinks(contacts));
                    log.info("Request to crm: linkContact to company; body: {}", crmToEntityRequest);
                    CRMToEntityResponse crmToEntityResponse = amoCRMRestClient.linkCompany(companyId, crmToEntityRequest);
                    log.info("Response from crm: linkContact to company; body: {}", crmToEntityResponse);
                    sleepRequestTimeout();
                }
                if (i == offset) {
                    hint = null;
                }
            }
            log.info("Successfully downloaded");
        } finally {
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
    }
    @Override
    public void deleteContacts(int offset, boolean deleteFromFile) {
        deleteEntity(this.contactsKeeper, offset, deleteFromFile);
    }
    @Override
    public void deleteCompanies(int offset, boolean deleteFromFile) {
        deleteEntity(this.companiesKeeper, offset, deleteFromFile);
    }
    @Override
    public void deleteLeads(int offset, boolean deleteFromFile) {
        deleteEntity(this.leadsKeeper, offset, deleteFromFile);
    }

    private void deleteEntity(IdsKeeper idsKeeper, int offset, boolean deleteFromFile) {
        try {
            idsKeeper.read();
        } catch (FileReadingException e) {
            log.error("Exception {} when {}.read()", e.getMessage(), idsKeeper.getClass().getName());
        }
        List<String> ids = idsKeeper.getIds();
        for (int i = offset; i < ids.size(); i++) {
            log.info("iteration = {} / {}; completeness {}%", i, ids.size(), (i + 1) * 100 / ids.size());
            amoCRMSourceRestClient.deleteContact(ids.get(i));
            sleepDeleteTimeout();
        }
        if (deleteFromFile) {
            try {
                idsKeeper.delete(ids);
            } catch (FileReadingException | FileWritingException e) {
                log.error("Exception {} when {}.delete()", e.getMessage(), idsKeeper.getClass().getName());
            }
        }
    }
    private void keep(CRMContactResponse.Embedded.Contact contact) {
        if (contact == null) return;
        try {
            this.contactsKeeper.offer(String.valueOf(contact.id()));
        } catch (FileWritingException e) {
            log.error("error while writing contacts info");
            log.error("error when writing id = {} to contactsKeeper", contact.id());
        }
    }
    private void keep(CRMComplexLeadResponse crmComplexLeadResponse) {
        if (crmComplexLeadResponse.id() != null) {
            try {
                this.leadsKeeper.offer(String.valueOf(crmComplexLeadResponse.id()));
            } catch (FileWritingException e) {
                log.error("error while writing leads info");
                log.error("error when writing id = {} to leadsKeeper", crmComplexLeadResponse.id());
            }
        }
        if (crmComplexLeadResponse.companyId() != null) {
            try {
                this.companiesKeeper.offer(String.valueOf(crmComplexLeadResponse.companyId()));
            } catch (FileWritingException e) {
                log.error("error while writing companies info");
                log.error("error when writing id = {} to companiesKeeper", crmComplexLeadResponse.companyId());
            }
        }
        if (crmComplexLeadResponse.contactId() != null) {
            try {
                this.contactsKeeper.offer(String.valueOf(crmComplexLeadResponse.contactId()));
            } catch (FileWritingException e) {
                log.error("error while writing contacts info");
                log.error("error when writing id = {} to contactsKeeper", crmComplexLeadResponse.contactId());
            }
        }
    }
    private void keep(CRMCompanyResponse.Embedded.Company company) {
        if (company == null) return;
        try {
            this.companiesKeeper.offer(String.valueOf(company.id()));
        } catch (FileWritingException e) {
            log.error("error while writing companies info");
            log.error("error when writing id = {} to companiesKeeper", company.id());
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
    private void sleepRequestTimeout() {
        this.sleep(requestTimeout);
    }
    private void sleepDeleteTimeout() {
        this.sleep(deleteRequestTimeout);
    }
    private void sleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}