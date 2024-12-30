package com.example.dbeaver_migration_mappers.crm_models.embedded;

import com.example.dbeaver_migration_mappers.crm_models.entity.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMContact;
import com.example.dbeaver_migration_mappers.crm_models.util.Tag;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public record EmbeddedLead(
        List<CRMContact> contacts,
        List<CRMCompany> companies,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<Tag> tags) {
}
