package com.example.dbeaver_migration_mappers.enums.contact;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ContactDear implements ValueEnum {
    FEMALE(1349949, "Уважаемая"),
    MALE(1349947, "Уважаемый");
    private final int enumId;
    private final String value;
    public static final int fieldId = 2922037;
    public static ContactDear of(String value) {
        for (ContactDear cd : values()) {
            if (value.equals(cd.getValue())) {
                return cd;
            }
        }
        throw new IllegalArgumentException("Wrong value for ContactDear: " + value);
    }
}
