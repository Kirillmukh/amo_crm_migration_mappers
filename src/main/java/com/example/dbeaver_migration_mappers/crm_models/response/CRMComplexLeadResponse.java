package com.example.dbeaver_migration_mappers.crm_models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CRMComplexLeadResponse(
        Integer id,
        @JsonProperty("contact_id")
        Integer contactId,
        @JsonProperty("company_id")
        Integer companyId,
        boolean merged
) {
}