package com.example.dbeaver_migration_mappers.crm_models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CRMToEntityLinksResponse(
        @JsonProperty("_links")
        Links links,
        @JsonProperty("_embedded")
        Embedded embedded
        ) {
    record Embedded(List<EmbeddedLinks> links) {
        record EmbeddedLinks(
                @JsonProperty("entity_id")
                int entityId,
                @JsonProperty("to_entity_id")
                int toEntityId,
                @JsonProperty("to_entity_type")
                String toEntityType
        ) {}
    }
}
