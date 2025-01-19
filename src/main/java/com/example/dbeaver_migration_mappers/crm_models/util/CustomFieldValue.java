package com.example.dbeaver_migration_mappers.crm_models.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CustomFieldValue (@JsonProperty("field_id") int fieldId, List<Value> values) {
}
