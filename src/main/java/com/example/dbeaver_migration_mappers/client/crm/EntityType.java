package com.example.dbeaver_migration_mappers.client.crm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EntityType {
    CONTACT("contacts"),
    LEAD("leads"),
    COMPANY("companies");
    private final String name;
}
