package com.example.dbeaver_migration_mappers.service;

import com.example.dbeaver_migration_mappers.client.AmoCRMRestClient;
import com.example.dbeaver_migration_mappers.crm_models.embedded.EmbeddedLead;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMContact;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMLead;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMContactRequest;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMLeadRequest;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMToEntityRequest;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMComplexLeadResponse;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMContactResponse;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMToEntityResponse;
import com.example.dbeaver_migration_mappers.mapper.ToEntityRequestMapper;
import com.example.dbeaver_migration_mappers.util.file.exception.FileWritingException;
import com.example.dbeaver_migration_mappers.util.keeper.CompaniesKeeper;
import com.example.dbeaver_migration_mappers.util.keeper.ContactsKeeper;
import com.example.dbeaver_migration_mappers.util.keeper.LeadsKeeper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// TODO: 04.01.2025 Сделать паузу между запросами
// TODO: 04.01.2025 Переделать сохранение контактов

@Service
@RequiredArgsConstructor
@Slf4j
public class AmoRequestService implements RequestService {
    private final AmoCRMRestClient amoCRMRestClient;
    private final ContactsKeeper contactsKeeper;
    private final CompaniesKeeper companiesKeeper;
    private final LeadsKeeper leadsKeeper;
    private final ToEntityRequestMapper toEntityRequestMapper;
    @Value("${config.crm.request.limit}")
    private int requestLimit;
    @Value("${config.crm.request.timeout_seconds}")
    private int requestTimeout;

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
        List<CRMComplexLeadResponse> complexLeadResponses = groupedComplexLead.stream()
                .map(amoCRMRestClient::createComplexLead)
                .flatMap(Collection::stream)
                .map(this::keep)
                .toList();
        log.info("complexLeadResponses size = {}", complexLeadResponses.size());

        if (complexLeadResponses.size() != groupedContacts.size()) {
            String message = "Different length in groupedComplexLead and groupedContacts";
            log.error(message);
            throw new IllegalStateException(message);
        }

        // contact request and link it with lead
        for (int i = 0; i < groupedContacts.size(); i++) {
            if (groupedContacts.get(i) == null) {
                continue;
            }
            CRMComplexLeadResponse crmComplexLeadResponse = complexLeadResponses.get(i);
            List<CRMToEntityResponse> crmToEntityResponses = groupedContacts.get(i).stream()
                    .map(amoCRMRestClient::createContact)
                    .map(contacts -> contacts.embedded().contacts())
                    .map(this::keep)
                    .map(toEntityRequestMapper::mapContactsToLeadLinks)
                    .map(CRMToEntityRequest::new)
                    .map(toEntity -> amoCRMRestClient.linkLead(crmComplexLeadResponse.id(), toEntity))
                    .toList();
            log.info("crmToEntityResponses: {}", crmToEntityResponses.stream().map(response -> response.embedded().links()).toList());
        }
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
        // contacts         -> /contacts
        /// split contacts by 40 entities
        // link contacts    -> /leads/link
    }

    private List<CRMContactResponse.Embedded.Contact> keep(List<CRMContactResponse.Embedded.Contact> contacts) {
        contacts.stream()
                .filter(Objects::nonNull)
                .forEach(contact -> {
                    try {
                        this.contactsKeeper.offer(String.valueOf(contact.id()));
                    } catch (FileWritingException e) {
                        log.error("error while writing contacts info");
                    }
                });
        return contacts;
    }

    private CRMComplexLeadResponse keep(CRMComplexLeadResponse crmComplexLeadResponse) {
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
        return crmComplexLeadResponse;
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