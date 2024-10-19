package com.example.dbeaver_migration_mappers.enums.company;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CompanyIndustry implements ValueEnum {
    FMCG_FOOD(1473139, "FMCG/Food"),
    FMCG_NONFOOD(1473141, "FMCG/Nonfood"),
    FMCG_WHOLESALE(1473143, "FMCG/Оптовая торговля"),
    IT_INTEGRATION(1473145, "IT/Интеграция"),
    IT_EQUIPMENT(1473147, "IT/Оборудование"),
    ANOTHER(1473149, "Другое"),
    ENGINEERING_CONDITIONS(1473151, "Инжиниринговые услуги"),
    MUNICIPAL_SERVICE(1473153,"Коммунальное хозяйство"),
    CORPORATION(1473155, "Корпорации"),
    LIGHT_INDUSTRY(1473157, "Легкая промышленность"),
    MECHANICAL_ENGINEERING(1473159, "Машиностроение"),
    AUTOMOTIVE_ENGINEERING(1473161, "Машиностроение/Автомобилестроение"),
    MEDIA(1473163, "Медиа"),
    MEDIA_CATALOGUE(1473165, "Медиа / Каталоги"),
    MEDIA_CATALOGUE_ANOTHER(1473167, "Медиа / Каталоги (левые)"),
    ASSOCIATION_BODIES(1473169, "Органы/Ассоциации"),
    STATE_BODIES(1473171, "Органы/Государственные органы"),
    EDUCATIONAL_INSTITUTIONS_BODIES(1473173, "Органы/Образовательные учреждения"),
    MSW_PROCESSING(1473175, "Переработка ТБО"),
    REALTORS_AND_DEVELOPERS(1473177, "Риэлторы и Девелоперы"),
    RETAIL_TRADE_CAR_DEALERS(1473179, "Розничная торговля/Автодилеры"),
    RETAIL_TRADE_PHARMACIES(1473181, "Розничная торговля/Аптеки"),
    RETAIL_TRADE_HOUSEHOLD_APPLIANCES(1473183, "Розничная торговля/Бытовая техника"),
    RETAIL_TRADE_CHILDRENS_GOODS(1473185, "Розничная торговля/Детские товары"),
    RETAIL_TRADE_OTHER(1473187, "Розничная торговля/Другое"),
    RETAIL_TRADE_FURNITURE(1473189, "Розничная торговля/Мебель"),
    RETAIL_TRADE_CLOTHES_AND_SHOES(1473191, "Розничная торговля/Одежда и обувь"),
    RETAIL_TRADE_NETWORK(1473193, "Розничная торговля/Сетевая"),
    AGRICULTURE(1473195, "Сельское хозяйство"),
    CONSTRUCTION(1473197, "Строительство"),
    ROAD_CONSTRUCTION(1473199, "Строительство/Дорожное строительство"),
    PRODUCTION_OF_CONSTRUCTION_MATERIALS(1473201, "Строительство/Производство стройматериалов"),
    OTHER_RAW_MATERIALS(1473203, "Сырье другое"),
    WOOD_PROCESSING_RAW_MATERIALS(1473205, "Сырье/Лесопереработка"),
    METALLURGY_RAW_MATERIALS(1473207, "Сырье/Металлургия"),
    CHEMISTRY_RAW_MATERIALS(1473209, "Сырье/Химия"),
    TELECOM(1473211, "Телеком"),
    CARGO_TRANSPORTATION_AND_LOGISTICS(1473213, "Транспорт/Грузоперевозки и Логистика"),
    PASSENGER_TRANSPORTATION(1473215, "Транспорт/Пассажироперевозки"),
    NUCLEAR_AND_ELECTRIC_POWER(1473217, "ТЭК/Атомная и электроэнергетика"),
    OIL_AND_GAS(1473219, "ТЭК/Нефть и Газ"),
    COAL(1473221, "ТЭК/Уголь"),
    PACKAGING(1473223, "Упаковка"),
    BUSINESS_SERVICE_HR_AGENCIES(1473225, "Услуги для бизнеса/HR агенства"),
    BUSINESS_SERVICE_PR_AGENCIES(1473227, "Услуги для бизнеса/PR агенства"),
    BUSINESS_SERVICE_EXHIBITION_COMPANIES(1473229, "Услуги для бизнеса/Выставочные компании"),
    BUSINESS_SERVICE_DISTRIBUTING(1473231, "Услуги для бизнеса/Дистрибуция"),
    BUSINESS_SERVICE_OTHER(1473233, "Услуги для бизнеса/Другое"),
    BUSINESS_SERVICE_NETWORK_AGENCIES(1473235, "Услуги для бизнеса/Интернет агенства"),
    BUSINESS_SERVICE_RESEARCH_COMPANIES(1473237, "Услуги для бизнеса/Исследовательские компании"),
    BUSINESS_SERVICE_MARKETING_AND_BTL_AGENCIES(1768289, "Услуги для бизнеса/Маркетинговые и BTL агентства"),
    BUSINESS_SERVICE_ADVERTISING_AGENCIES(1473241, "Услуги для бизнеса/Рекламные агенства"),
    BUSINESS_SERVICE_MANAGEMENT_CONSULTING(1473243, "Услуги для бизнеса/Управленческий консалтинг"),
    BUSINESS_SERVICE_LEGAL_COMPANIES(1473245, "Услуги для бизнеса/Юридические компании"),
    POPULATION_SERVICE_HORECA(1473247, "Услуги для населения/HoReCa"),
    POPULATION_SERVICE_HOUSEHOLD_SERVICES(1473249, "Услуги для населения/Бытовые услуги"),
    POPULATION_SERVICE_LEISURE(1473251, "Услуги для населения/Досуг"),
    POPULATION_SERVICE_COMMERCIAL_MEDICINE(1473253, "Услуги для населения/Коммерческая медицина"),
    PHARMACY_ENGINEERING_AND_DISTRIBUTING(1473257, "Фарма производство и дистрибуция"),
    FINANCE_OTHER(1473259, "Финансы другое"),
    FINANCE_CORPORATE_BANKS(1473261, "Финансы/Банки Корпоративные"),
    FINANCE_UNIVERSAL(1473263, "Финансы/Банки Универсальные"),
    FINANCE_INVESTING(1473265, "Финансы/Инвестиционные"),
    FINANCE_COLLECTION_AGENCIES(1473267, "Финансы/Коллекторские агентства"),
    FINANCE_LEASING(1473269, "Финансы/Лизинг"),
    FINANCE_INSURANCE(1473271, "Финансы/Страховые"),
    ELECTRONIC_TRADE(1473273, "Электронная торговля"),
    NULL(1491175, "NULL"),
    PUBLISHING_OR_PRINTING_HOUSES(1783169, "Издательства / Типографии");
    private final int enumId;
    private final String value;
    public static final int fieldId = 2933361;
    public static CompanyIndustry of(String value) {
        for (CompanyIndustry ci : values()) {
            if (value.equals(ci.getValue())) {
                return ci;
            }
        }
        return NULL;
//        throw new IllegalArgumentException("Wrong value for CompanyIndustry: " + value);
    }
}
