package com.example.dbeaver_migration_mappers.enums.contact;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ContactModeration implements ValueEnum {
    ON_CONTROL(1768371, "На контроль"),
    NOT_CONFIRMED(1768373, "Не подтверждено"),
    CHECKED(1768375, "Проверено");
    private final int enumId;
    private final String value;
    public static final int fieldId = 3013403;
    public static ContactModeration of(String value) {
        for (ContactModeration cd : values()) {
            if (value.equals(cd.getValue())) {
                return cd;
            }
        }
        throw new IllegalArgumentException("Wrong value for ContactModeration: " + value);
    }
}
