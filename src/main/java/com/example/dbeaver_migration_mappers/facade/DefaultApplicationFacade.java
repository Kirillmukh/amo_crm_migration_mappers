package com.example.dbeaver_migration_mappers.facade;

import com.example.dbeaver_migration_mappers.client.AmoCRMRestClient;
import com.example.dbeaver_migration_mappers.client.DatabaseRestClient;
import com.example.dbeaver_migration_mappers.client.HateoasLinkRestClient;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMLeadRequest;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMLead;
import com.example.dbeaver_migration_mappers.input_models.hateoas.Link;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestLead;
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
    private final DatabaseRestClient<RequestLead, ListHateoasEntity<RequestLead>> leadDatabaseRestClient;
    private final AmoCRMRestClient amoCRMRestClient;
    private final LeadMapper leadMapper;
    private final HateoasLinkRestClient<ListHateoasEntity<RequestLead>> hateoasLinkRestClient;
    @Value("${config.crm.request.timeout_seconds}")
    private int requestTimeout;
    @Value("${config.crm.request.limit}")
    private int requestLimit;

    @Override
    public void loadComplexLead() {
        // запрос на сервер бд
        List<RequestLead> requestDatabaseLeads = requestContent();

        // маппинг под CRM
        CRMLeadRequest crmLeadRequest = new CRMLeadRequest(leadMapper.mapRequestLead(requestDatabaseLeads));

        // группировка по 50 элементов
        List<CRMLeadRequest> crmLeadRequestRequests = groupCRMLeadRequest(crmLeadRequest);

        // запрос в срм в отдельном потоке
        sendComplexLeads(crmLeadRequestRequests);

    }

    // TODO: 30.12.2024 save response 
    private void sendComplexLeads(List<CRMLeadRequest> crmLeadRequestRequests) {
        new Thread(() -> {
            crmLeadRequestRequests.forEach(requestBody -> {
                amoCRMRestClient.createComplexLead(requestBody);
                try {
                    Thread.sleep(requestTimeout * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }, "Load data to CRM").start();
    }

    private List<CRMLeadRequest> groupCRMLeadRequest(CRMLeadRequest crmLeadRequest) {
        int size = crmLeadRequest.crmLead().size();
        size = size / 50 + (size % 50 > 0 ? 1 : 0);
        List<CRMLeadRequest> crmLeadRequestRequests = new ArrayList<>(size);
        Stream<CRMLead> requestLeadStream = crmLeadRequest.crmLead().stream();
        for (int i = 0; i < size; i++) {
            crmLeadRequestRequests.add(new CRMLeadRequest(requestLeadStream.limit(requestLimit).collect(Collectors.toList())));
            requestLeadStream = requestLeadStream.skip(requestLimit);
        }
        return crmLeadRequestRequests;
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

    @Override
    public void loadLeadsByGUID(List<String> guids) {
        List<CRMLead> collect = guids.stream()
                .map(leadDatabaseRestClient::requestById)
                .map(optional -> {
                    if (optional.isEmpty()) {
                        log.error("HateoasLeadDatabaseRestClient class return response with empty entity. Maybe id is wrong");
                        throw new RuntimeException("HateoasLeadDatabaseRestClient class return response with empty entity");
                    }
                    return optional.get();
                })
                .map(leadMapper::mapRequestLead)
                .collect(Collectors.toList());

        List<CRMLeadRequest> crmLeadRequests = groupCRMLeadRequest(new CRMLeadRequest(collect));

        sendLead(crmLeadRequests);
    }

    // TODO: 30.12.2024 save response 
    private void sendLead(List<CRMLeadRequest> crmLeadRequestRequests) {
        new Thread(() -> {
            crmLeadRequestRequests.forEach(requestBody -> {
                amoCRMRestClient.createLead(requestBody);
                try {
                    Thread.sleep(requestTimeout * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }, "Load data to CRM").start();
    }
}
