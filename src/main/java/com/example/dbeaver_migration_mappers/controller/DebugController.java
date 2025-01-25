package com.example.dbeaver_migration_mappers.controller;

import com.example.dbeaver_migration_mappers.client.collector.RequestCollector;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMContact;
import com.example.dbeaver_migration_mappers.crm_models.entity.wrapper.CRMCompanyCRMContactsListWrapper;
import com.example.dbeaver_migration_mappers.crm_models.entity.wrapper.CRMCompanyCRMContactsWrapper;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMCompanyRequest;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMContactRequest;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMCompanyResponse;
import com.example.dbeaver_migration_mappers.input_models.InputCompany;
import com.example.dbeaver_migration_mappers.input_models.InputContact;
import com.example.dbeaver_migration_mappers.input_models.request.RequestCompanyWithContactsDTO;
import com.example.dbeaver_migration_mappers.mapper.CompanyMapper;
import com.example.dbeaver_migration_mappers.mapper.ContactMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("debug")
@Slf4j
public class DebugController {
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private RequestCollector<RequestCompanyWithContactsDTO> requestCompanyWithContactsCollector;

    @Value("${config.crm.request.limit}")
    private int requestLimit;

    @GetMapping("log")
    public void log() throws IOException {

    }
    @GetMapping("company")
    public DebugResponse mapCompany() {
        List<RequestCompanyWithContactsDTO> requestDatabaseCompaniesWithContacts = requestCompanyWithContactsCollector.requestContent();

        List<CRMCompanyCRMContactsWrapper> crmCompanyCRMContactsWrappers = requestDatabaseCompaniesWithContacts.stream()
                .map(request -> new CRMCompanyCRMContactsWrapper(
                        companyMapper.mapToOutput(request.getCompany()),
                        contactMapper.mapToOutput(request.getContacts())))
                .toList();
        List<DebugResponse.Entity> entities = crmCompanyCRMContactsWrappers.stream().map(company -> new DebugResponse.Entity(company.crmCompany(), company.crmContact())).toList();
        CRMCompanyCRMContactsListWrapper crmCompanyRequest = new CRMCompanyCRMContactsListWrapper(crmCompanyCRMContactsWrappers);
        List<List<List<CRMContact>>> groupedContacts = crmCompanyRequest.list().stream()
                .map(CRMCompanyCRMContactsWrapper::crmContact)
                .map(this::group)
                .toList();
        List<CRMCompany> companiesQueue = crmCompanyRequest.list().stream().map(CRMCompanyCRMContactsWrapper::crmCompany).toList();
        List<CRMCompanyRequest> groupedCompanies = group(companiesQueue).stream().map(CRMCompanyRequest::new).toList();
        return new DebugResponse(groupedCompanies, groupedContacts, entities);
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
    record DebugResponse(List<CRMCompanyRequest> companyRequests, List<List<List<CRMContact>>> contactsRequest,
                         List<Entity> entity) {
        record Entity(CRMCompany companyRequest,
                      List<CRMContact> contactRequests) {

        }
    }
    @PostMapping("contact")
    public CRMContact mapContact(@RequestBody InputContact inputCompany) {
        return this.contactMapper.mapToOutput(inputCompany);
    }
}
