package com.example.dbeaver_migration_mappers.enums.contact;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public final class ContactEmail implements ValueEnum {
    private final int enumId;
    private final String value;
    private static final int fieldId = 1720453;
    public ContactEmail(String value, Type type) {
        this.enumId = type.getEnumId();
        this.value = value;
    }
    @RequiredArgsConstructor
    @Getter
    public enum Type {
        WORK(773149),
        PERSONALITY(773151),
        OTHER(773153);
        private final int enumId;
    }
}
