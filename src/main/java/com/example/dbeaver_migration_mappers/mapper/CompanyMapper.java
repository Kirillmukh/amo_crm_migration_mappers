package com.example.dbeaver_migration_mappers.mapper;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import com.example.dbeaver_migration_mappers.enums.company.CompanyCategory;
import com.example.dbeaver_migration_mappers.input_models.InputCompany;
import com.example.dbeaver_migration_mappers.output_models.constant.CompanyFieldsID;
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

        CompanyCategory category = CompanyCategory.of(input.getCategory());
        list.add(new CustomFieldValue(CompanyFieldsID.CATEGORY, List.of(createValue(category))));

        list.add(new CustomFieldValue(CompanyFieldsID.EDM, List.of(createValue(input.isUsrCompanyUseEDM()))));
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
