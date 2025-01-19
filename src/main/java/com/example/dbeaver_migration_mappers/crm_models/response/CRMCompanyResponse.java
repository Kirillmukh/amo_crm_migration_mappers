package com.example.dbeaver_migration_mappers.crm_models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CRMCompanyResponse(
        @JsonProperty("_links")
        Links links,
        @JsonProperty("_embedded")
        Embedded embedded) {
    public record Embedded(List<Company> companies) {
        public record Company(int id, @JsonProperty("_links") Links links) {
        }
    }
}
