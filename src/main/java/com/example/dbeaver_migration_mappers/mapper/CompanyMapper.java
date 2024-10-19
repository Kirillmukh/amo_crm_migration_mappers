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
        list.add(new CustomFieldValue(CompanyFieldsID.ALTERNATIVE_NAME, List.of(createValue(input.getAlternativeName()))));

        CompanyType type = CompanyType.of(input.getType());
        list.add(new CustomFieldValue(CompanyFieldsID.TYPE, List.of(createValue(type))));

        list.add(new CustomFieldValue(CompanyFieldsID.WEB, List.of(createValue(input.getWeb()))));

        CompanyPhone phone = new CompanyPhone(input.getPhone(), CompanyPhone.Type.MOBILE); // MOBILE?
        list.add(new CustomFieldValue(CompanyFieldsID.PHONE, List.of(createValue(phone))));

        CompanyCategory category = CompanyCategory.of(input.getCategory());
        list.add(new CustomFieldValue(CompanyFieldsID.CATEGORY, List.of(createValue(category))));

        CompanyIndustry industry = CompanyIndustry.of(input.getIndustry());
        list.add(new CustomFieldValue(CompanyFieldsID.INDUSTRY, List.of(createValue(industry))));

        list.add(new CustomFieldValue(CompanyFieldsID.EDM, List.of(createValue(input.isUsrCompanyUseEDM()))));

        // TODO REPLACE: USE LEAD
        List<Value> events = new ArrayList<>();
        for (String event : input.getUsrArchiveEvents().split(" ")) {
            if (CompanyEvent.contains(event)) {
                events.add(createValue(CompanyEvent.of(event)));
            }
        }
        list.add(new CustomFieldValue(CompanyFieldsID.EVENTS, events));

        list.add(new CustomFieldValue(CompanyFieldsID.NOTES, List.of(createValue(input.getUsrPrimKontr()))));
        return list;
    }
    private Value createValue(String value) {
        return new Value(value, null);
    }
    private Value createValue(Boolean value) {
        return new Value(value, null);
    }
    private Value createValue(ValueEnum valueEnum) {
        return new Value(valueEnum.getValue(), valueEnum.getEnumId());
    }
}
