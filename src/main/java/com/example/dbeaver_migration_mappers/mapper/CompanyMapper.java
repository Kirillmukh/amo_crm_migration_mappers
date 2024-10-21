package com.example.dbeaver_migration_mappers.mapper;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import com.example.dbeaver_migration_mappers.enums.company.*;
import com.example.dbeaver_migration_mappers.input_models.InputCompany;
import com.example.dbeaver_migration_mappers.output_models.response.OutputCompany;
import com.example.dbeaver_migration_mappers.output_models.util.CustomFieldValue;
import com.example.dbeaver_migration_mappers.output_models.util.Value;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.ArrayList;
import java.util.List;

import static com.example.dbeaver_migration_mappers.output_models.constants.CompanyFieldsID.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompanyMapper {

    @Mapping(target = "customFieldValues", expression = "java(setCustomFields(inputCompany))")
    OutputCompany mapToOutput(InputCompany inputCompany);
    default List<CustomFieldValue> setCustomFields(InputCompany input) {
        List<CustomFieldValue> list = new ArrayList<>();
        list.add(new CustomFieldValue(ALTERNATIVE_NAME, singleValue(input.getAlternativeName())));

        CompanyType type = CompanyType.of(input.getType());
        list.add(new CustomFieldValue(TYPE, singleValue(type)));

        list.add(new CustomFieldValue(WEB, singleValue(input.getWeb())));

        CompanyPhone phone = new CompanyPhone(input.getPhone(), CompanyPhone.Type.WORK); // WORK? MOBILE?
        list.add(new CustomFieldValue(PHONE, singleValue(phone)));

        CompanyCategory category = CompanyCategory.of(input.getCategory());
        list.add(new CustomFieldValue(CATEGORY, singleValue(category)));

        CompanyIndustry industry = CompanyIndustry.of(input.getIndustry());
        list.add(new CustomFieldValue(INDUSTRY, singleValue(industry)));

        list.add(new CustomFieldValue(EDM, singleValue(input.isUsrCompanyUseEDM())));

        // TODO REPLACE: USE LEAD?
        List<Value> events = new ArrayList<>();
        for (String event : input.getUsrOldEvents().split(" ")) {
            if (CompanyEvent.contains(event)) {
                events.add(new Value(CompanyEvent.of(event)));
            }
        }
        list.add(new CustomFieldValue(EVENTS, events));

        list.add(new CustomFieldValue(NOTES, singleValue(input.getUsrPrimKontr())));
        return list;
    }
    private List<Value> singleValue(Object value) {
        return List.of(new Value(value));
    }
    private List<Value> singleValue(ValueEnum value) {
        return List.of(new Value(value));
    }
}
