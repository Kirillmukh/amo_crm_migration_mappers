package com.example.dbeaver_migration_mappers.enums.contact;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ContactType implements ValueEnum {

    CLIENT(1349927, "Клиент"),
    SPONSOR(1491879, "Спонсор"),
    DEKRET(1349921, "Декрет"),
    NOT_ACTUAL(1489803, "НЕ АКТУАЛЕН"),
    PRESENTATOR(1349923, "Докладчик (только)"),
    DUBL(1349925, "Дубль"),
    LEAD(1489801, "Лид"),
    MISSING_EVENTS(1489805, "Не посещает мероприятия"),
    MISSING_PERSONALITY(1489807, "Обезличенный"),
    PARTNER(1489809, "Партнер"),
    SUBSCRIBE(1489811, "Подписка"),
    EMPLOYER(1489969, "Сотрудник Интерфорум"),
    NULL(1491881, "NULL"),
    INFOPARTNER(1491883, "Инфопартнер");
    private final int enumId;
    private final String value;
    public static final int fieldId = 2922033;
    public static ContactType of(String value) {
        for (ContactType type : values()) {
            if (value.equals(type.getValue())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Wrong value for ContactType: " + value);
    }
}
