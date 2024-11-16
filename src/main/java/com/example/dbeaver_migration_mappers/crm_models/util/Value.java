package com.example.dbeaver_migration_mappers.crm_models.util;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Value (Object value, Integer enumId) {
    public Value(Object value) {
        this(value, null);
    }
    public Value(ValueEnum valueEnum) {
        this(valueEnum.getValue(), valueEnum.getEnumId());
    }
}
