package com.example.dbeaver_migration_mappers.output_models.util;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Value (Object value, Integer enumId) {
}
