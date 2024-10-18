package com.example.dbeaver_migration_mappers.enums.company;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import lombok.Getter;

@Getter
public enum CompanyCategory implements ValueEnum {
    OUR_TEAM(1491147, "0 Наши участники"),
    NOT_OUR_TEAM(1491165, "0 Не наши участники"),
    FIRST_RATINGS(1780781, "1 Рейтинги"),
    COMPANIES(1780783, "2 Компании"),
    SMALL_COMPANIES(1491159, "3 Мелкие компании"),
    TINY_COMPANIES(1491163, "4 Очень мелкие компании"),
    DEPENDENT_COMPANIES(1491811, "5 Зависимые компании"),
    SPEAKERS_AND_PARTNER(1491157, "6 Спикеры и инфопартнеры"),
    DISAPPEARED_COMPANIES(1491153, "7 Компания закончила существование"),
    AGENCIES(1491149, "8 Агентства");
    CompanyCategory(int enumId, String value) {
        this.enumId = enumId;
        this.value = value;
    }
    private final int enumId;
    private final String value;
    public static final int fieldId = 2922069;
    public static CompanyCategory of(String value) {
        for (CompanyCategory category : values()) {
            if (value.equals(category.getValue())) {
                return category;
            }
        }
        throw new IllegalArgumentException("Wrong value for CompanyCategory: " + value);
    }
}
