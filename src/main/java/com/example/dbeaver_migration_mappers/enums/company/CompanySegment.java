package com.example.dbeaver_migration_mappers.enums.company;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CompanySegment implements ValueEnum {
    B2B(1768271, "B2B"),
    B2B_PLUS_B2C(1768273, "B2B + B2C"),
    B2C(1768275, "B2C"),
    DEFAULT__NOT_PROCEED(1768277, "Не обработано (по умолчанию)"),
    UNKNOWN(1768279, "Не определено");
    private final int enumId;
    private final String value;
    public static final int fieldId = 3013329;
    public static CompanySegment of(String value) {
        for (CompanySegment cs : values()) {
            if (value.equals(cs.getValue())) {
                return cs;
            }
        }
        throw new IllegalArgumentException("Wrong value for CompanySegment: " + value);
    }
}
