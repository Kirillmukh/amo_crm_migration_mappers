package com.example.dbeaver_migration_mappers.crm_models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CRMContactResponse(
        @JsonProperty("_links")
        Links links,
        @JsonProperty("_embedded")
        Embedded embedded) {
    record Embedded(List<Contact> contacts) {
        record Contact(int id, @JsonProperty("_links") Links links) {
        }
    }
}
