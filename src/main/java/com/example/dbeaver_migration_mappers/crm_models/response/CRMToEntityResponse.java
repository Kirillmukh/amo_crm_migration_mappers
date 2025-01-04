package com.example.dbeaver_migration_mappers.crm_models.response;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CRMToEntityResponse(
        @JsonProperty("_links")
        Links links,
        @JsonProperty("_embedded")
        Embedded embedded) {
    public record Embedded(List<EmbeddedLinks> links) {
        public record EmbeddedLinks(
                @JsonProperty("to_entity_id")
                int toEntityId,
                @JsonProperty("to_entity_type")
                String toEntityType
        ) {}
    }
}
