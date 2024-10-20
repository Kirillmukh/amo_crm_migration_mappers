package com.example.dbeaver_migration_mappers.enums.contact;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ContactRole implements ValueEnum {
    FIRST_FACE(1349951, "Первое лицо"),
    BOSS(1349953, "Начальник"),
    SPECIALIST(1349955, "Специалист"),
    NULL(1491169, "NULL");
    private final int enumId;
    private final String value;
    public static final int fieldId = 2922043;
    public static ContactRole of(String value) {
        for (ContactRole cd : values()) {
            if (value.equals(cd.getValue())) {
                return cd;
            }
        }
        return NULL;
//        throw new IllegalArgumentException("Wrong value for ContactRole: " + value);
    }
}
