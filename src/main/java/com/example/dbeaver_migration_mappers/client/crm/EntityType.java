package com.example.dbeaver_migration_mappers.client.crm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EntityType {
    CONTACT("contact", "contacts"),
    LEAD("lead", "leads"),
    COMPANY("company", "companies");
    private final String value;
    private final String values;
    public static EntityType fromValues(String values) {
        for (EntityType entityType : values()) {
            if (values.equals(entityType.getValues())) {
                return entityType;
            }
        }
        throw new IllegalArgumentException("Wrong value for EntityType: " + values);
    }
    @Override
    public String toString() {
        return this.getValues();
    }
}
