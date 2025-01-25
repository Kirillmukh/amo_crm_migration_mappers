package com.example.dbeaver_migration_mappers.facade;

import com.example.dbeaver_migration_mappers.client.collector.RequestCollector;
import com.example.dbeaver_migration_mappers.client.hateoas_link.CompanyWithContactsHateoasRestClient;
import com.example.dbeaver_migration_mappers.crm_models.entity.wrapper.CRMCompanyCRMContactsListWrapper;
import com.example.dbeaver_migration_mappers.crm_models.entity.wrapper.CRMCompanyCRMContactsWrapper;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMContactRequest;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMLeadRequest;
import com.example.dbeaver_migration_mappers.input_models.request.RequestCompanyWithContactsDTO;
import com.example.dbeaver_migration_mappers.input_models.request.RequestContactWithoutCompanyDTO;
import com.example.dbeaver_migration_mappers.input_models.request.RequestLead;
import com.example.dbeaver_migration_mappers.mapper.CompanyMapper;
import com.example.dbeaver_migration_mappers.mapper.ContactMapper;
import com.example.dbeaver_migration_mappers.mapper.LeadMapper;
import com.example.dbeaver_migration_mappers.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultApplicationFacade implements ApplicationFacade {
    private final RequestService requestService;
    private final LeadMapper leadMapper;
    private final ContactMapper contactMapper;
    private final CompanyMapper companyMapper;
    private final RequestCollector<RequestLead> requestLeadCollector;
    private final RequestCollector<RequestCompanyWithContactsDTO> requestCompanyWithContactsCollector;
    private final RequestCollector<RequestContactWithoutCompanyDTO> requestContactWithoutCompanyCollector;
    @Override
    public void loadComplexLead() {
        // запрос на сервер бд
        List<RequestLead> requestDatabaseLeads = requestLeadCollector.requestContent();

        // маппинг под CRM
        CRMLeadRequest crmLeadRequest = new CRMLeadRequest(leadMapper.mapRequestLead(requestDatabaseLeads));

        // Не протестировано на реальных кейсах
        // requestService.saveComplexLead(crmLeadRequest);
    }

    @Override
    public void loadLeadsByGUID(List<String> guids) {
//        List<CRMLead> collect = guids.stream()
//                .map(leadDatabaseRestClient::requestById)
//                .map(optional -> {
//                    if (optional.isEmpty()) {
//                        log.error("HateoasLeadDatabaseRestClient class return response with empty entity. Maybe id is wrong");
//                        throw new RuntimeException("HateoasLeadDatabaseRestClient class return response with empty entity");
//                    }
//                    return optional.get();
//                })
//                .map(leadMapper::mapRequestLead)
//                .toList();
//
//        CRMLeadRequest crmLeadRequest = new CRMLeadRequest(collect);
//
//        requestService.saveComplexLead(crmLeadRequest);
    }
    @Override
    public CRMCompanyCRMContactsListWrapper loadCompaniesAndContacts() {
        List<RequestCompanyWithContactsDTO> requestDatabaseCompaniesWithContacts = requestCompanyWithContactsCollector.requestContent();
//         DEBUG
        requestDatabaseCompaniesWithContacts = requestDatabaseCompaniesWithContacts.subList(0, 15);
//         DEBUG

        log.info("requestDatabaseCompaniesWithContacts = {}", requestDatabaseCompaniesWithContacts);
//        if (true) throw new RuntimeException("break point");

        List<CRMCompanyCRMContactsWrapper> crmCompanyCRMContactsWrappers = requestDatabaseCompaniesWithContacts.stream()
                .map(request -> new CRMCompanyCRMContactsWrapper(
                        companyMapper.mapToOutput(request.getCompany()),
                        contactMapper.mapToOutput(request.getContacts())))
                .toList();

//        if (true) throw new RuntimeException("break point");


        CRMCompanyCRMContactsListWrapper crmCompanyRequest = new CRMCompanyCRMContactsListWrapper(crmCompanyCRMContactsWrappers);

        requestService.saveCompanyAndContacts(crmCompanyRequest);
        return crmCompanyRequest;
    }
    @Override
    public CRMContactRequest loadContactsWithoutCompany() {
        List<RequestContactWithoutCompanyDTO> requestDatabaseContactsWithoutCompanies = requestContactWithoutCompanyCollector.requestContent();

        log.info("requestDatabaseContactsWithoutCompanies = {}", requestDatabaseContactsWithoutCompanies);
        CRMContactRequest crmContactRequest = new CRMContactRequest(contactMapper.mapToOutputRequestContactWithoutCompany(requestDatabaseContactsWithoutCompanies));

        if (true) throw new RuntimeException("security | break point");
        requestService.saveContact(crmContactRequest);
        return crmContactRequest;
    }
    @Override
    public void rollback() {
        requestService.deleteEntities();
    }
}
