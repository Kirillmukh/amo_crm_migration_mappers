package com.example.dbeaver_migration_mappers.crm_models.response;

import com.example.dbeaver_migration_mappers.crm_models.embedded.EmbeddedCompany;
import com.example.dbeaver_migration_mappers.crm_models.util.CustomFieldValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CRMCompaniesResponse(
        @JsonProperty("_links")
        Links links,
        @JsonProperty("_embedded")
        Embedded embedded
) {
    public record Embedded(List<Company> companies) {
        public record Company(
                int id,
                String name,
                @JsonProperty("custom_fields_values")
                List<CustomFieldValue> customFieldValues,
                @JsonInclude(JsonInclude.Include.NON_EMPTY)
                EmbeddedCompany _embedded
        ) {

        }
    }
}
