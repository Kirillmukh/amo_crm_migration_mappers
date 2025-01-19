package com.example.dbeaver_migration_mappers.crm_models.request;

import com.example.dbeaver_migration_mappers.crm_models.entity.CRMCompany;

import java.util.List;

public record CRMCompanyRequest(List<CRMCompany> crmCompanyList) {
}
