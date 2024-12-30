package com.example.dbeaver_migration_mappers.crm_models.request;

import com.example.dbeaver_migration_mappers.crm_models.entity.CRMLead;

import java.util.List;


public record CRMLeadRequest(List<CRMLead> crmLead) {
}
