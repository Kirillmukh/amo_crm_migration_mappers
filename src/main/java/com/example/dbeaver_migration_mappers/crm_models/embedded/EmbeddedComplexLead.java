package com.example.dbeaver_migration_mappers.crm_models.embedded;

import com.example.dbeaver_migration_mappers.crm_models.entity.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMContact;

public record EmbeddedComplexLead(
        CRMContact crmContact,
        CRMCompany company
) {
}
