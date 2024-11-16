package com.example.dbeaver_migration_mappers.crm_models.util;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public record Tag(String name, Integer id) {
}
