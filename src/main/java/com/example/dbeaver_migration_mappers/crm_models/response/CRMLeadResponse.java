package com.example.dbeaver_migration_mappers.crm_models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CRMLeadResponse(
        @JsonProperty("_links")
        Links links,
        @JsonProperty("_embedded")
        Embedded embedded) {
    public record Embedded(List<Lead> leads) {
        public record Lead(int id, @JsonProperty("_links") Links links) {
        }
    }
}