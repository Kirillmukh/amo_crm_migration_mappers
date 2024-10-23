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

        if (!input.getWeb().isBlank()) {
            list.add(new CustomFieldValue(WEB, singleValue(input.getWeb())));
        }

        if (!input.getPhone().isBlank()) {
            CompanyPhone phone = new CompanyPhone(input.getPhone(), CompanyPhone.Type.WORK);
            list.add(new CustomFieldValue(PHONE, singleValue(phone)));
        }

        CompanyCategory category = CompanyCategory.of(input.getCategory());
        list.add(new CustomFieldValue(CATEGORY, singleValue(category)));

        CompanyIndustry industry = CompanyIndustry.of(input.getIndustry());
        list.add(new CustomFieldValue(INDUSTRY, singleValue(industry)));

        list.add(new CustomFieldValue(EDM, singleValue(input.isUsrCompanyUseEDM()))); // TODO CHECK THAT 'false' USE IN REQUEST OR NOT

        if (!input.getUsrOldEvents().isBlank()) {
            List<Value> events = new ArrayList<>();
            for (String event : input.getUsrOldEvents().split(" ")) {
                if (CompanyEvent.contains(event)) {
                    events.add(new Value(CompanyEvent.of(event)));
                }
            }
            list.add(new CustomFieldValue(EVENTS, events));
        }

        if (!input.getUsrPrimKontr().isBlank()) {
            list.add(new CustomFieldValue(NOTES, singleValue(input.getUsrPrimKontr())));
        }

        CompanySegment segment = CompanySegment.of(input.getSegment());
        list.add(new CustomFieldValue(SEGMENT, singleValue(segment)));

        if (input.getModeration() != null) {
            CompanyModeration moderation = CompanyModeration.of(input.getModeration());
            list.add(new CustomFieldValue(MODERATION, singleValue(moderation)));
        }

        CompanyFirstLetter firstLetter = CompanyFirstLetter.of(input.getName());
        list.add(new CustomFieldValue(FIRST_LETTER, singleValue(firstLetter)));

        return list;
    }
    private List<Value> singleValue(Object value) {
        return List.of(new Value(value));
    }
    private List<Value> singleValue(ValueEnum value) {
        return List.of(new Value(value));
    }
}
