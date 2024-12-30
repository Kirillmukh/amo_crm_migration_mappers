package com.example.dbeaver_migration_mappers.client;

import com.example.dbeaver_migration_mappers.client.crm.EntityType;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMLeadRequest;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMComplexLeadResponseWrapper;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMLeadResponse;
import com.example.dbeaver_migration_mappers.crm_models.util.ResponseTag;
import com.example.dbeaver_migration_mappers.crm_models.util.Tag;

import java.util.List;

public interface AmoCRMRestClient {
    CRMCompany getCompany(int id);
    ResponseTag createTags(List<Tag> tags, EntityType entityType);
    ResponseTag getTags(EntityType et, int page);
    CRMComplexLeadResponseWrapper createComplexLead(CRMLeadRequest crmLeadRequest);
    CRMLeadResponse createLead(CRMLeadRequest crmLeadRequest);

}
