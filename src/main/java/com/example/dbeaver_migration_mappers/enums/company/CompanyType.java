package com.example.dbeaver_migration_mappers.enums.company;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import lombok.Getter;

@Getter
public enum CompanyType implements ValueEnum {
    INFOPARTNER(1349965, "Инфопартнер"),
    CLIENT(1349967, "Клиент"),
    OUR_COMPANY(1349969, "Наша компания"),
    SPONSOR(1349971, "Спонсор"),
    DUBL(1489967, "Дубль"),
    NULL(1491173, "NULL"),
    PODRYADCHIK(1782921, "Подрядчик");
    CompanyType(int enumId, String value) {
        this.enumId = enumId;
        this.value = value;
    }
    private final int enumId;
    private final String value;
    public static final int fieldId = 2922061;
    public static CompanyType of(String value) {
        for (CompanyType type : values()) {
            if (value.equals(type.getValue())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Wrong value for CompanyType: " + value);
    }
}
