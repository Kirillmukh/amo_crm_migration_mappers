package com.example.dbeaver_migration_mappers.enums.contact;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ContactDepartment implements ValueEnum {
    ANALYTICS_AND_RESEARCH(1504247, "Аналитика и исследования"),
    BANKS_COMMERCIAL_BUSINESS(1504249, "Банки Корпоративный бизнес"),
    BANKS_CREDITS(1504259, "Банки Залоги и Задолженности"),
    ECONOMIC_SECURITY(1504261, "Безопасность Экономическая"),
    ACCOUNTING(1504263, "Бухгалтерия"),
    BANKS_RISKS_MANAGEMENT(1504281, "Банки Управление Рисками"),
    PURCHASES(1504251, "Закупки"),
    OPERATION_ACTIONS(1504255, "Операционная деятельность"),
    OPPORTUNITIES_E_COM(1504253, "Продажи - E-com"),
    MANAGEMENT(1504257, "Управление персоналом"),
    PRODUCTION(1504265, "Производство"),
    TECHNOLOGIES_AND_PROCESSES(1504267, "Технологии и процессы"),
    ECOLOGY(1504269, "Экология"),
    SERVICES(1504271, "Услуги"),
    INVITES_TO_EVENTS(1504273, "Приглашения на мероприятия"),
    MEDIA_EDITOR(1504275, "Медиа Редактор"),
    BANKS_OPERATION_MANAGEMENT(1504277, "Банки Операционное управление"),
    MEDIA_SITE_KEEPING(1504279, "Медиа Развитие Сайта"),
    BANKS_TECHNOLOGIES_AND_PROCESSES(1504283, "Банки Технологии и Процессы"),
    PROJECT_MANAGEMENT(1504285, "Управление проектами"),
    LOGISTICS_AND_TRANSPORT(1504287, "Логистика и транспорт"),
    MARKETING_DATA_SCIENCE(1504289, "Маркетинг - анализ данных"),
    BRAND_PRODUCT_MANAGERS(1504291, "Бренд/продакт менеджеры"),
    UNKNOWN(1504293, "Неопределено"),
    BANKS_RETAIL_BUSINESS(1504295, "Банки Розничный бизнес"),
    MARKETING(1504297, "Маркетинг"),
    OPPORTUNITIES(1504299, "Продажи"),
    BANKS_REMOTE_MAINTENANCE(1504301, "Банки Дистанционное обслуживание"),
    QUALITY_MANAGEMENT(1504303, "Управление качеством"),
    DATABASE_MANAGEMENT(1504305, "Управление Базами Данных"),
    INTERNAL_COMMUNICATIONS(1504307, "Внутренние связи"),
    DEVICE_AND_PROTOCOL(1504309, "Аппарат и протокол"),
    LEGAL(1504311, "Юристы"),
    ADVERTISEMENT(1504313, "Реклама"),
    INVESTOR_COMMUNICATION(1504315, "Связь с инвесторами"),
    EVENT_PRODUCING(1504317, "Продюсирование мероприятий"),
    FINANCE_MANAGEMENT(1504319, "Управление Финансами"),
    STRATEGY(1504321, "Стратегия"),
    DESIGN(1504323, "Дизайн"),
    AGRICULTURE(1504325, "Хозяйственная деятельность"),
    INTERNAL_CONTROL_OR_AUDIT(1504327, "Внутренний контроль / Аудит"),
    IT(1504329, "Информационные технологии"),
    COMPANY_DEVELOPMENT(1504331, "Развитие Компании"),
    HELPERS(1504333, "Помощники"),
    CLIENT_SERVICE(1504335, "Клиентский сервис"),
    TRADE_MARKET(1504337, "Маркетинг торговый"),
    BANKS_REGIONAL_DEVELOPMENT(1504339, "Банки Региональное развитие"),
    MANAGEMENT_C_AND_B(1504341, "Управление персоналом - C&B"),
    EXTERNAL_COMMUNICATIONS(1504343, "Внешние связи"),
    EDUCATION(1504345, "Обучение"),
    PRODUCT_MANAGEMENT(1777357, "Управление продуктами");
    private final int enumId;
    private final String value;
    public static final int fieldId = 2967615;
    public static ContactDepartment of(String value) {
        for (ContactDepartment cd : values()) {
            if (value.equals(cd.getValue())) {
                return cd;
            }
        }
        if (value.equals("Бренд и категорийные менеджеры")) return BRAND_PRODUCT_MANAGERS;
        throw new IllegalArgumentException("Wrong value for ContactDepartment: " + value);
    }
}
