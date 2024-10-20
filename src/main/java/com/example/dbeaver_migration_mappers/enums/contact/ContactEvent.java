package com.example.dbeaver_migration_mappers.enums.contact;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum ContactEvent implements ValueEnum {
    KO24_2D(1783171, "KO24/2д"),
    KO24_2Y(1783173, "KO24/2у"),
    KO24_2(1783175, "KO24/2"),
    ES24_2D(1783177, "ES24/2д"),
    ES24_2Y(1783179, "ES24/2у"),
    ES24_2(1783181, "ES24/2"),
    MDA24_2D(1783183, "MDA24/2д"),
    MDA24_2Y(1783185, "MDA24/2у"),
    MDA24_2(1783187, "MDA24/2"),
    IT24_2D(1783189, "IT24/2д"),
    IT24_2Y(1783191, "IT24/2у"),
    IT24_2(1783193, "IT24/2"),
    PAF24_2D(1783195, "PAF24/2д"),
    PAF24_2Y(1783197, "PAF24/2у"),
    PAF24_2(1783199, "PAF24/2"),
    B2B24_2D(1783201, "B2B24/2д"),
    B2B24_2Y(1783203, "B2B24/2у"),
    B2B24_2(1783205, "B2B24/2");
    private final int enumId;
    private final String value;
    public static final int fieldId = 3022555;
    public static ContactEvent of(String value) {
        for (ContactEvent ce : values()) {
            if (value.equals(ce.getValue())) {
                return ce;
            }
        }
        throw new IllegalArgumentException("Wrong value for CompanyEvent: " + value);
    }
    private static final Set<String> values = Arrays.stream(values()).map(ContactEvent::getValue).collect(Collectors.toSet());
    public static boolean contains(String value) {
        return values.contains(value);
    }
}
