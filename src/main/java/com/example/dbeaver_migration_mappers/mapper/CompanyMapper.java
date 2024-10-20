package com.example.dbeaver_migration_mappers.mapper;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import com.example.dbeaver_migration_mappers.enums.company.*;
import com.example.dbeaver_migration_mappers.input_models.InputCompany;
import com.example.dbeaver_migration_mappers.output_models.constants.CompanyFieldsID;
import com.example.dbeaver_migration_mappers.output_models.response.OutputCompany;
import com.example.dbeaver_migration_mappers.output_models.util.CustomFieldValue;
import com.example.dbeaver_migration_mappers.output_models.util.Value;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompanyMapper {

    @Mapping(target = "customFieldValues", expression = "java(setCustomFields(inputCompany))")
    OutputCompany mapToOutput(InputCompany inputCompany);
    default List<CustomFieldValue> setCustomFields(InputCompany input) {
        List<CustomFieldValue> list = new ArrayList<>();
        list.add(new CustomFieldValue(CompanyFieldsID.ALTERNATIVE_NAME, List.of(new Value(input.getAlternativeName()))));

        CompanyType type = CompanyType.of(input.getType());
        list.add(new CustomFieldValue(CompanyFieldsID.TYPE, List.of(new Value(type))));

        list.add(new CustomFieldValue(CompanyFieldsID.WEB, List.of(new Value(input.getWeb()))));

        CompanyPhone phone = new CompanyPhone(input.getPhone(), CompanyPhone.Type.WORK); // WORK? MOBILE?
        list.add(new CustomFieldValue(CompanyFieldsID.PHONE, List.of(new Value(phone))));

        CompanyCategory category = CompanyCategory.of(input.getCategory());
        list.add(new CustomFieldValue(CompanyFieldsID.CATEGORY, List.of(new Value(category))));

        CompanyIndustry industry = CompanyIndustry.of(input.getIndustry());
        list.add(new CustomFieldValue(CompanyFieldsID.INDUSTRY, List.of(new Value(industry))));

        list.add(new CustomFieldValue(CompanyFieldsID.EDM, List.of(new Value(input.isUsrCompanyUseEDM()))));

        // TODO REPLACE: USE LEAD
        List<Value> events = new ArrayList<>();
        for (String event : input.getUsrArchiveEvents().split(" ")) {
            if (CompanyEvent.contains(event)) {
                events.add(new Value(CompanyEvent.of(event)));
            }
        }
        list.add(new CustomFieldValue(CompanyFieldsID.EVENTS, events));

        list.add(new CustomFieldValue(CompanyFieldsID.NOTES, List.of(new Value(input.getUsrPrimKontr()))));
        return list;
    }
}
