package com.example.dbeaver_migration_mappers.controller;

import com.example.dbeaver_migration_mappers.client.AmoCRMSourceRestClient;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMContact;
import com.example.dbeaver_migration_mappers.input_models.InputCompany;
import com.example.dbeaver_migration_mappers.input_models.InputContact;
import com.example.dbeaver_migration_mappers.mapper.CompanyMapper;
import com.example.dbeaver_migration_mappers.mapper.ContactMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("debug")
@RequiredArgsConstructor
@Slf4j
public class DebugController {
    private final CompanyMapper companyMapper;
    private final ContactMapper contactMapper;
    private final AmoCRMSourceRestClient amoCRMSourceRestClient;
    @GetMapping("log")
    public void log() throws IOException {

    }
    @GetMapping("delete_range")
    public void deleteRange(@RequestParam int from, @RequestParam int to) {
        if (true) return;
        if (from % 2 == 0) return;
        for (int id = from; id <= to; id += 2) {
            amoCRMSourceRestClient.deleteContact(String.valueOf(id));
        }
    }
    @PostMapping("company")
    public CRMCompany mapCompany(@RequestBody InputCompany inputCompany) {
        return companyMapper.mapToOutput(inputCompany);
    }
    @PostMapping("contact")
    public CRMContact mapContact(@RequestBody InputContact inputCompany) {
        return this.contactMapper.mapToOutput(inputCompany);
    }
}
