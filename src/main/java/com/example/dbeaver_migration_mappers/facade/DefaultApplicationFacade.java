package com.example.dbeaver_migration_mappers.facade;

import com.example.dbeaver_migration_mappers.client.collector.RequestCollector;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMContact;
import com.example.dbeaver_migration_mappers.crm_models.entity.wrapper.CRMCompanyCRMContactsListWrapper;
import com.example.dbeaver_migration_mappers.crm_models.entity.wrapper.CRMCompanyCRMContactsWrapper;
import com.example.dbeaver_migration_mappers.crm_models.entity.wrapper.InputCompaniesCRMContactsWrapper;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMContactRequest;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMLeadRequest;
import com.example.dbeaver_migration_mappers.input_models.InputCompany;
import com.example.dbeaver_migration_mappers.input_models.request.RequestCompanyWithContactsDTO;
import com.example.dbeaver_migration_mappers.input_models.request.RequestContactAndCompany;
import com.example.dbeaver_migration_mappers.input_models.request.RequestContactWithoutCompanyDTO;
import com.example.dbeaver_migration_mappers.input_models.request.RequestLead;
import com.example.dbeaver_migration_mappers.mapper.CompanyMapper;
import com.example.dbeaver_migration_mappers.mapper.ContactMapper;
import com.example.dbeaver_migration_mappers.mapper.LeadMapper;
import com.example.dbeaver_migration_mappers.service.RequestService;
import com.example.dbeaver_migration_mappers.util.file.JsonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private final RequestCollector<RequestContactAndCompany> requestContactAndCompanyRequestCollector;
    private final JsonService jsonService;
    @Override
    public void saveComplexLead() {
        List<RequestLead> requestDatabaseLeads = requestLeadCollector.requestContent();

        CRMLeadRequest crmLeadRequest = new CRMLeadRequest(leadMapper.mapRequestLead(requestDatabaseLeads));

        jsonService.saveObject(crmLeadRequest);
        log.info("data saved to CRMLeadRequest.json");
    }
    @Override
    public void saveNewContacts() {
        List<RequestContactWithoutCompanyDTO> requestDatabaseContactsWithoutCompanies = requestContactWithoutCompanyCollector.requestContent();

        CRMContactRequest crmContactRequest = new CRMContactRequest(contactMapper.mapToOutputRequestContactWithoutCompany(requestDatabaseContactsWithoutCompanies));

        jsonService.saveObject(crmContactRequest);
        log.info("data saved to CRMContactRequest.json");
    }
    @Override
    public void saveNewCompaniesNewContacts() {
        List<RequestCompanyWithContactsDTO> requestDatabaseCompaniesWithContacts = requestCompanyWithContactsCollector.requestContent();

        List<CRMCompanyCRMContactsWrapper> crmCompanyCRMContactsWrappers = requestDatabaseCompaniesWithContacts.stream()
                .map(request -> new CRMCompanyCRMContactsWrapper(
                        companyMapper.mapToOutput(request.getCompany()),
                        contactMapper.mapToOutput(request.getContacts())))
                .toList();
        CRMCompanyCRMContactsListWrapper crmCompanyRequest = new CRMCompanyCRMContactsListWrapper(crmCompanyCRMContactsWrappers);

        jsonService.saveObject(crmCompanyRequest);
        log.info("data saved to CRMCompanyCRMContactsListWrapper.json");
    }
    @Override
    public void saveNewContactsOldCompanies() {
        List<RequestContactAndCompany> requestContactAndCompanies = requestContactAndCompanyRequestCollector.requestContent();
        Map<InputCompany, List<CRMContact>> groupedByCompany = new HashMap<>();

        // group by company
        requestContactAndCompanies.forEach(companyAndContact -> {
            InputCompany inputCompany = companyAndContact.getCompany();
            if (!groupedByCompany.containsKey(inputCompany)) {
                groupedByCompany.put(inputCompany, new ArrayList<>());
            }
            CRMContact contact = contactMapper.mapToOutput(companyAndContact.getContact());
            groupedByCompany.get(inputCompany).add(contact);
        });


        List<InputCompany> inputCompanies = new ArrayList<>(groupedByCompany.keySet());
        List<List<CRMContact>> contacts = new ArrayList<>();
        inputCompanies.forEach(company ->
                contacts.add(groupedByCompany.get(company))
        );

        InputCompaniesCRMContactsWrapper wrapper = new InputCompaniesCRMContactsWrapper(inputCompanies, contacts);
        jsonService.saveObject(wrapper);
        log.info("data saved to InputCompaniesCRMContactsWrapper.json");
    }

    @Override
    public void loadComplexLead(int offset) {
        CRMLeadRequest crmLeadRequest = jsonService.loadObject(CRMLeadRequest.class);

        // Не протестировано на реальных кейсах
        requestService.saveComplexLead(crmLeadRequest, offset);
        log.info("Выгрузка закончена успешно");
    }
    @Override
    public void loadNewContacts(int offset) {
        CRMContactRequest crmContactRequest = jsonService.loadObject(CRMContactRequest.class);

        requestService.saveNewContacts(crmContactRequest, offset);
        log.info("Выгрузка закончена успешно");
    }
    @Override
    public void loadNewCompanies(int offset) {
        CRMCompanyCRMContactsListWrapper crmCompanyRequest = jsonService.loadObject(CRMCompanyCRMContactsListWrapper.class);

        requestService.saveNewContactsOldCompanies(crmCompanyRequest, offset);
        log.info("Выгрузка закончена успешно");
    }
    @Override
    public void loadNewContactsOldCompanies(int offset) {
        InputCompaniesCRMContactsWrapper inputCompaniesCRMContactsWrapper = jsonService.loadObject(InputCompaniesCRMContactsWrapper.class);

        requestService.saveNewCompaniesNewContacts(inputCompaniesCRMContactsWrapper, offset);
        log.info("Выгрузка закончена успешно");
    }

    @Override
    public void deleteContacts(int offset, boolean deleteFromFile) {
        requestService.deleteContacts(offset, deleteFromFile);
    }
    @Override
    public void deleteCompanies(int offset, boolean deleteFromFile) {
        requestService.deleteCompanies(offset, deleteFromFile);
    }
    @Override
    public void deleteLeads(int offset, boolean deleteFromFile) {
        requestService.deleteLeads(offset, deleteFromFile);
    }
}
