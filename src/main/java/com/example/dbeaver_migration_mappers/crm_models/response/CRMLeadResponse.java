package com.example.dbeaver_migration_mappers.crm_models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CRMLeadResponse(
        @JsonProperty("_links")
        Links links,
        @JsonProperty("_embedded")
        Embedded embedded) {
    record Embedded(List<Lead> leads) {
        record Lead(int id, @JsonProperty("_links") Links links) {
        }
    }

    record Links(Self self) {
        record Self(String href) {
        }
    }
}