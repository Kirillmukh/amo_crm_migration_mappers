package com.example.dbeaver_migration_mappers.facade;

import com.example.dbeaver_migration_mappers.client.AmoCRMRestClient;
import com.example.dbeaver_migration_mappers.client.DatabaseRestClient;
import com.example.dbeaver_migration_mappers.client.HateoasLinkRestClient;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMComplexLead;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMContact;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMLead;
import com.example.dbeaver_migration_mappers.input_models.InputCompany;
import com.example.dbeaver_migration_mappers.input_models.InputContact;
import com.example.dbeaver_migration_mappers.input_models.InputLead;
import com.example.dbeaver_migration_mappers.input_models.hateoas.Link;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestCompany;
import com.example.dbeaver_migration_mappers.input_models.request.RequestLead;
import com.example.dbeaver_migration_mappers.mapper.CompanyMapper;
import com.example.dbeaver_migration_mappers.mapper.ContactMapper;
import com.example.dbeaver_migration_mappers.mapper.LeadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultApplicationFacade implements ApplicationFacade {
    private final DatabaseRestClient<RequestCompany, ListHateoasEntity<RequestCompany>> companyDatabaseRestClient;
    private final DatabaseRestClient<RequestLead, ListHateoasEntity<RequestLead>> leadDatabaseRestClient;
    private final AmoCRMRestClient amoCRMRestClient;
    private final CompanyMapper companyMapper;
    private final ContactMapper contactMapper;
    private final LeadMapper leadMapper;
    private final HateoasLinkRestClient<ListHateoasEntity<RequestLead>> hateoasLinkRestClient;
    @Value("${config.crm.request.timeout_seconds}")
    private int requestTimeout;
    @Value("${config.crm.request.limit}")
    private int requestLimit;

    // TODO: 05.12.2024 DELETE THIS METHOD
    @Override
    public void transferComplexCompany() {
        Optional<ListHateoasEntity<RequestCompany>> optionalHateoasRequest = companyDatabaseRestClient.request();
        if (optionalHateoasRequest.isEmpty()) {
            log.error("HateoasCompanyDatabaseRestClient.class return response with empty entity");
            throw new RuntimeException("HateoasCompanyDatabaseRestClient.class return response with empty entity");
        }
        ListHateoasEntity<RequestCompany> hateoasRequestCompanyList = optionalHateoasRequest.get();

        List<RequestCompany> requestCompanyList = hateoasRequestCompanyList.content();

        requestCompanyList.forEach(requestCompany -> {
            InputCompany company = requestCompany.getCompany();
            List<InputContact> contacts = requestCompany.getContacts();
            List<InputLead> leads = requestCompany.getLeads();


            CRMCompany crmCompany = companyMapper.mapToOutput(company);
            List<CRMContact> crmContacts = contactMapper.mapToOutput(contacts);
            List<CRMLead> crmLeads = leadMapper.mapInputLead(leads);

            /*
            // todo
            разделить CRmComplexCompany на list по 50 объектов\
            добавить засыпание на 30 сек для запросов
            проверить на null crmCompany, crmContacts, crmLeads перед загрузкой в CRM
             */

//            CRMComplexCompany complexCompanyCRM = new CRMComplexCompany(crmCompany, crmContacts, crmLeads);

//            amoCRMRestClient.createComplexCompany(complexCompanyCRM);

        });
    }
    @Override
    public void loadComplexLead() {
        // запрос на сервер бд
        List<RequestLead> requestDatabaseLeads = requestContent();

        // маппинг под CRM
        CRMComplexLead crmComplexLead = new CRMComplexLead(leadMapper.mapRequestLead(requestDatabaseLeads));

        // группировка по 50 элементов
        List<CRMComplexLead> crmComplexLeadRequests = groupCRMComplexLead(crmComplexLead);

        // запрос в срм в отдельном потоке
        sendComplexLeads(crmComplexLeadRequests);

    }

    private void sendComplexLeads(List<CRMComplexLead> crmComplexLeadRequests) {
        new Thread(() -> {
            crmComplexLeadRequests.forEach(requestBody -> {
                amoCRMRestClient.createComplexLead(requestBody);
                try {
                    Thread.sleep(requestTimeout * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }, "Load data to CRM").start();
    }
    private List<CRMComplexLead> groupCRMComplexLead(CRMComplexLead crmComplexLead) {
        int size = crmComplexLead.crmLead().size();
        size = size / 50 + (size % 50 > 0 ? 1 : 0);
        List<CRMComplexLead> crmComplexLeadRequests = new ArrayList<>(size);
        Stream<CRMLead> requestLeadStream = crmComplexLead.crmLead().stream();
        for (int i = 0; i < size; i++) {
            crmComplexLeadRequests.add(new CRMComplexLead(requestLeadStream.limit(requestLimit).collect(Collectors.toList())));
            requestLeadStream = requestLeadStream.skip(requestLimit);
        }
        return crmComplexLeadRequests;
    }
    private List<RequestLead> requestContent() {
        Optional<ListHateoasEntity<RequestLead>> request = leadDatabaseRestClient.request();
        if (request.isEmpty()) {
            log.error("empty result from leadDatabaseRestClient.request() in DefaultApplicationFacade.java");
            throw new RuntimeException("empty result from leadDatabaseRestClient.request() in DefaultApplicationFacade.java");
        }

        ListHateoasEntity<RequestLead> databaseRequest = request.get();
        List<RequestLead> content = databaseRequest.content();
        Link linkToNext = null;

        for (var link : databaseRequest.links()) {
            if (link.rel().equals("next")) {
                linkToNext = link;
                break;
            }
        }

        if (linkToNext == null) {
            log.error("linkToNext is null in DefaultApplicationFacade.java" +
                    "may be request is not contains next rel");
            throw new RuntimeException("linkToNext is null in DefaultApplicationFacade.java" +
                    "may be request is not contains next rel");
        }
        content.addAll(requestContentHelper(linkToNext));
        return content;
    }
    private List<RequestLead> requestContentHelper(Link linkToNext) {
        Optional<ListHateoasEntity<RequestLead>> optionalRequest = hateoasLinkRestClient.request(linkToNext);

        if (optionalRequest.isEmpty()) {
            log.error("empty result from leadDatabaseRestClient.request() in DefaultApplicationFacade.java");
            throw new RuntimeException("empty result from leadDatabaseRestClient.request() in DefaultApplicationFacade.java");
        }

        ListHateoasEntity<RequestLead> request = optionalRequest.get();

        List<RequestLead> result = request.content();
        linkToNext = null;

        for (var link : request.links()) {
            if (link.rel().equals("next")) {
                linkToNext = link;
                break;
            }
        }

        if (linkToNext == null) {
            return result;
        }
        result.addAll(requestContentHelper(linkToNext));
        return result;
    }
    // delete this method
    @Override
    public void loadCompaniesByUUID(List<String> guids) {
        guids.forEach(guid -> {
            Optional<RequestCompany> optionalHateoasRequest = companyDatabaseRestClient.requestById(guid);
            if (optionalHateoasRequest.isEmpty()) {
                log.error("HateoasCompanyDatabaseRestClient.class return response with empty entity");
                throw new RuntimeException("HateoasCompanyDatabaseRestClient.class return response with empty entity for id = " + guid);
            }
            RequestCompany hateoasRequestCompany = optionalHateoasRequest.get();
            // TODO: 05.12.2024 send request
        });
    }
}
