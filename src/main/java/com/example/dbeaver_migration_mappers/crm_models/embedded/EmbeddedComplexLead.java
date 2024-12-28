package com.example.dbeaver_migration_mappers.crm_models.embedded;

import com.example.dbeaver_migration_mappers.crm_models.request.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMContact;

public record EmbeddedComplexLead(
        CRMContact crmContact,
        CRMCompany company
) {
}
