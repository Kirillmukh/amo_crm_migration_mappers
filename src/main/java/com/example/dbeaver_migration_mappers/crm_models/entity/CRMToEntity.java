package com.example.dbeaver_migration_mappers.crm_models.entity;

import com.example.dbeaver_migration_mappers.client.crm.EntityType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CRMToEntity {
    @JsonProperty("to_entity_id")
    private int toEntityId;
    @JsonProperty("to_entity_type")
    private EntityType toEntityType;
}