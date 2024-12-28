package com.example.dbeaver_migration_mappers.crm_models.embedded;

import com.example.dbeaver_migration_mappers.crm_models.request.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMContact;
import com.example.dbeaver_migration_mappers.crm_models.util.Tag;

import java.util.List;

public record EmbeddedLead(List<CRMContact> contacts, List<CRMCompany> companies, List<Tag> tags) {
}
