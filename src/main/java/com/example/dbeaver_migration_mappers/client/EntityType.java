package com.example.dbeaver_migration_mappers.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EntityType {
    CONTACT("Contact"),
    LEAD("Lead"),
    COMPANY("Company");
    private final String name;
}
