package com.example.dbeaver_migration_mappers.facade;

import com.example.dbeaver_migration_mappers.client.AmoCRMRestClient;
import com.example.dbeaver_migration_mappers.client.DatabaseRestClient;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMComplexCompany;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMContact;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMLead;
import com.example.dbeaver_migration_mappers.input_models.InputCompany;
import com.example.dbeaver_migration_mappers.input_models.InputContact;
import com.example.dbeaver_migration_mappers.input_models.InputLead;
import com.example.dbeaver_migration_mappers.input_models.request.RequestCompany;
import com.example.dbeaver_migration_mappers.mapper.CompanyMapper;
import com.example.dbeaver_migration_mappers.mapper.ContactMapper;
import com.example.dbeaver_migration_mappers.mapper.LeadMapper;
import com.example.dbeaver_migration_mappers.service.ResponseService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultApplicationFacade implements ApplicationFacade {
    private final DatabaseRestClient companyDatabaseRestClient;
    private final AmoCRMRestClient amoCRMRestClient;
    private final ResponseService responseService;
    private final CompanyMapper companyMapper;
    private final ContactMapper contactMapper;
    private final LeadMapper leadMapper;
    @Override
    public void transferComplexCompany() {
        RequestCompany requestCompany;
        try (Response companyResponse = companyDatabaseRestClient.request()) {
            requestCompany = responseService.getRequestCompany(companyResponse);
        }
        if (requestCompany == null) {
            log.error("ResponseService.class couldn't getRequestCompany or companyDatabaseRestClient return response with empty or non-company entity");
            throw new RuntimeException("ResponseService.class couldn't getRequestCompany or companyDatabaseRestClient return response with empty or non-company entity");
        }

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
        CRMComplexCompany complexCompanyCRM = new CRMComplexCompany(crmCompany, crmContacts, crmLeads);

        amoCRMRestClient.createComplexCompany(complexCompanyCRM);
    }
}
