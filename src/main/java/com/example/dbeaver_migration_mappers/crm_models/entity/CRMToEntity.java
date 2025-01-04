package com.example.dbeaver_migration_mappers.crm_models.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CRMToEntity {
    @JsonProperty("to_entity_id")
    private Integer toEntityId;
    @JsonProperty("to_entity_type")
    private String toEntityType;
}