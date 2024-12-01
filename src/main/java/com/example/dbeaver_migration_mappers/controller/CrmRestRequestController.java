package com.example.dbeaver_migration_mappers.controller;

import com.example.dbeaver_migration_mappers.controller.payload.GUIDListWrapper;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMLead;
import com.example.dbeaver_migration_mappers.facade.ApplicationFacade;
import com.example.dbeaver_migration_mappers.input_models.InputCompany;
import com.example.dbeaver_migration_mappers.input_models.InputLead;
import com.example.dbeaver_migration_mappers.mapper.CompanyMapper;
import com.example.dbeaver_migration_mappers.mapper.LeadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CrmRestRequestController {
    private final LeadMapper leadMapper;
    private final CompanyMapper companyMapper;
    private final ApplicationFacade applicationFacade;

    @PostMapping("test_lead")
    public CRMLead handleLead(@RequestBody InputLead inputLead) {
        return leadMapper.mapToOutput(inputLead);
    }

    @PostMapping("test_company")
    public CRMCompany handleCompany(@RequestBody InputCompany inputCompany) {
        return companyMapper.mapToOutput(inputCompany);
    }

    @GetMapping
    public void handle() {
        applicationFacade.transferComplexCompany();
    }
    @PostMapping
    public Object loadByUUID(@RequestBody GUIDListWrapper guids) {
        applicationFacade.loadCompaniesByUUID(guids.guids());
        // TODO: 01.12.2024
        return "error";
    }
}
