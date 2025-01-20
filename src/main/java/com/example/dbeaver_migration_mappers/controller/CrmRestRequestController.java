package com.example.dbeaver_migration_mappers.controller;

import com.example.dbeaver_migration_mappers.crm_models.entity.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMLead;
import com.example.dbeaver_migration_mappers.facade.ApplicationFacade;
import com.example.dbeaver_migration_mappers.input_models.InputCompany;
import com.example.dbeaver_migration_mappers.input_models.InputLead;
import com.example.dbeaver_migration_mappers.mapper.CompanyMapper;
import com.example.dbeaver_migration_mappers.mapper.LeadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CrmRestRequestController {
    private final LeadMapper leadMapper;
    private final CompanyMapper companyMapper;
    private final ApplicationFacade applicationFacade;

//    @PostMapping("test_lead")
//    public CRMLead handleLead(@RequestBody InputLead inputLead) {
//        return leadMapper.mapInputLead(inputLead);
//    }

    @PostMapping("test_company")
    public CRMCompany handleCompany(@RequestBody InputCompany inputCompany) {
        return companyMapper.mapToOutput(inputCompany);
    }

//    @GetMapping("handle-complex-lead")
//    public void handleComplexLead() {
//        applicationFacade.loadComplexLead();
//    }
    @GetMapping("handle-new-companies")
    public void handleNewCompanies() {
        applicationFacade.loadCompaniesAndContacts();
    }
    @GetMapping("handle-new-contacts")
    public void handleNewContacts() {
        applicationFacade.loadContactsWithoutCompany();
    }
    @DeleteMapping
    public void deleteAll() {
        applicationFacade.rollback();
    }
//    @PostMapping
//    public void loadByUUID(@RequestBody List<String> guids) {
//        applicationFacade.loadLeadsByGUID(guids);
//    }
}
