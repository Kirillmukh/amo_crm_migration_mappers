package com.example.dbeaver_migration_mappers.facade;

import com.example.dbeaver_migration_mappers.client.AmoCRMRestClient;
import com.example.dbeaver_migration_mappers.client.DatabaseRestClient;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMContact;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMLead;
import com.example.dbeaver_migration_mappers.input_models.InputCompany;
import com.example.dbeaver_migration_mappers.input_models.InputContact;
import com.example.dbeaver_migration_mappers.input_models.InputLead;
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

import java.util.List;
import java.util.Optional;

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
            List<CRMLead> crmLeads = leadMapper.mapToOutput(leads);

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
    public void transferComplexLead() {
        Optional<ListHateoasEntity<RequestLead>> optionalHateoasRequest = leadDatabaseRestClient.request();
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
