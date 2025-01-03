package com.example.dbeaver_migration_mappers.crm_models.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CRMToEntityLinks(
        @JsonProperty("entity_id")
        int entityId,
        @JsonProperty("to_entity_id")
        int toEntityId,
        @JsonProperty("to_entity_type")
        String toEntityType
) {
}
