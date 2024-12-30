package com.example.dbeaver_migration_mappers.crm_models.response;

public record CRMComplexLeadResponse(
        int id,
        int contact_id,
        int company_id,
        boolean merged
) {
}