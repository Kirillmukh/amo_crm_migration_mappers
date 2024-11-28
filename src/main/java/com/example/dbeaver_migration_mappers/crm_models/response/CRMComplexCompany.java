package com.example.dbeaver_migration_mappers.crm_models.response;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CRMComplexCompany { // TODO check docs and replace field by doc
    private CRMCompany company;
    private List<CRMContact> contacts;
    private List<CRMLead> leads;
}
