package com.example.dbeaver_migration_mappers.crm_models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CRMUpdateContactResponse(
        @JsonProperty("_links")
        Links links,
        @JsonProperty("_embedded")
        Embedded embedded) {
    public record Embedded(List<Contact> contacts) {
        public record Contact(
                int id,
                String name,
                @JsonProperty("updated_at")
                int updatedAt
        ) {
        }

    }

}
