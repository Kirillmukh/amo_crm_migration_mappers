package com.example.dbeaver_migration_mappers.enums.contact;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public final class ContactPhone implements ValueEnum {
    private final int enumId;
    private final String value;
    public static final int fieldId = 1720451;
    public ContactPhone(String value, Type type) {
        this.enumId = type.getEnumId();
        this.value = value;
    }
    @RequiredArgsConstructor
    @Getter
    public enum Type {
        WORK(773137),
        PRIME_WORK(773139),
        MOBILE(773141),
        FAX(773143),
        HOME(773145),
        OTHER(773147);
        private final int enumId;
    }
}
