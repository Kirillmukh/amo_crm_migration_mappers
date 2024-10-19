package com.example.dbeaver_migration_mappers.enums.company;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum CompanyEvent implements ValueEnum {
    KO24_2D(1783207, "KO24/2д"),
    KO24_2Y(1783209, "KO24/2у"),
    KO24_2(1783211, "KO24/2"),
    ES24_2D(1783213, "ES24/2д"),
    ES24_2Y(1783215, "ES24/2у"),
    ES24_2(1783217, "ES24/2"),
    MDA24_2D(1783219, "MDA24/2д"),
    MDA24_2Y(1783221, "MDA24/2у"),
    MDA24_2(1783223, "MDA24/2"),
    IT24_2D(1783225, "IT24/2д"),
    IT24_2Y(1783227, "IT24/2у"),
    IT24_2(1783229, "IT24/2"),
    PAF24_2D(1783231, "PAF24/2д"),
    PAF24_2Y(1783233, "PAF24/2у"),
    PAF24_2(1783235, "PAF24/2"),
    B2B24_2D(1783237, "B2B24/2д"),
    B2B24_2Y(1783239, "B2B24/2у"),
    B2B24_2(1783241, "B2B24/2");
    private final int enumId;
    private final String value;
    public static final int fieldId = 3022561;
    public static CompanyEvent of(String value) {
        for (CompanyEvent ce : values()) {
            if (value.equals(ce.getValue())) {
                return ce;
            }
        }
        throw new IllegalArgumentException("Wrong value for CompanyEvent: " + value);
    }
    private static final Set<String> values = Arrays.stream(values()).map(CompanyEvent::getValue).collect(Collectors.toSet());
    public static boolean contains(String value) {
        return values.contains(value);
    }
}
