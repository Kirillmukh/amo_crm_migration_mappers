package com.example.dbeaver_migration_mappers.enums.company;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CompanyModeration implements ValueEnum {
    ON_CONTROL(1768265, "На контроль"),
    NOT_CONFIRMED(1768267, "Не подтверждено"),
    CHECKED(1768269, "Проверено");
    private final int enumId;
    private final String value;
    public static final int fieldId = 3013325;
    public static CompanyModeration of(String value) {
        for (CompanyModeration cm : values()) {
            if (value.equals(cm.getValue())) {
                return cm;
            }
        }
        throw new IllegalArgumentException("Wrong value for CompanyModeration: " + value);
    }
}
