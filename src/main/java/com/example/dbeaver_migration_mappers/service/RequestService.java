package com.example.dbeaver_migration_mappers.service;

import com.example.dbeaver_migration_mappers.crm_models.request.CRMLeadRequest;

public interface RequestService {
    void saveComplexLead(CRMLeadRequest crmLeadRequest);
}
