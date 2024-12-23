package com.example.dbeaver_migration_mappers.crm_models.request;

import com.example.dbeaver_migration_mappers.crm_models.response.CRMLead;

import java.util.List;


public record CRMComplexLead(List<CRMLead> crmLead) {
}
