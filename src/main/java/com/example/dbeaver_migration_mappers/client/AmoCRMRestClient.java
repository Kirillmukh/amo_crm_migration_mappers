package com.example.dbeaver_migration_mappers.client;

import com.example.dbeaver_migration_mappers.client.crm.EntityType;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.util.ResponseTag;
import com.example.dbeaver_migration_mappers.crm_models.util.Tag;

import java.util.List;

public interface AmoCRMRestClient {
    CRMCompany getCompanies(int id);
    ResponseTag createTags(List<Tag> tags, EntityType entityType);
    ResponseTag getTags(EntityType et, int page);
}
