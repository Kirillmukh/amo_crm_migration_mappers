package com.example.dbeaver_migration_mappers.client;

import com.example.dbeaver_migration_mappers.output_models.response.OutputCompany;
import com.example.dbeaver_migration_mappers.output_models.util.Tag;

import java.util.List;

public interface AmoCRMRestClient {
    OutputCompany getCompanies(int id);
    List<Tag> createTags(List<Tag> tags, EntityType entityType);
}
