package com.example.dbeaver_migration_mappers.controller;

import com.example.dbeaver_migration_mappers.client.AmoCRMRestClient;
import com.example.dbeaver_migration_mappers.input_models.InputCompany;
import com.example.dbeaver_migration_mappers.input_models.InputLead;
import com.example.dbeaver_migration_mappers.mapper.CompanyMapper;
import com.example.dbeaver_migration_mappers.mapper.LeadMapper;
import com.example.dbeaver_migration_mappers.output_models.response.OutputCompany;
import com.example.dbeaver_migration_mappers.output_models.response.OutputLead;
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
    private final AmoCRMRestClient restClient;
    @PostMapping("lead")
    public OutputLead handleLead(@RequestBody InputLead inputLead) {
        return leadMapper.mapToOutput(inputLead);
    }
    @PostMapping("company")
    public OutputCompany handleCompany(@RequestBody InputCompany inputCompany) {
        return companyMapper.mapToOutput(inputCompany);
    }
    @GetMapping("client")
    public OutputCompany useClient() {
        return restClient.getCompanies(33057267);
    }
}
