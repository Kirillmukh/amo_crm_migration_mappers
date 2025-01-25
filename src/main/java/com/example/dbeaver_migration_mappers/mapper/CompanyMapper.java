package com.example.dbeaver_migration_mappers.mapper;

import com.example.dbeaver_migration_mappers.crm_models.embedded.EmbeddedCompany;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.util.CustomFieldValue;
import com.example.dbeaver_migration_mappers.crm_models.util.Tag;
import com.example.dbeaver_migration_mappers.crm_models.util.Value;
import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import com.example.dbeaver_migration_mappers.enums.company.*;
import com.example.dbeaver_migration_mappers.input_models.InputCompany;
import com.example.dbeaver_migration_mappers.util.EventMatcher;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.dbeaver_migration_mappers.crm_models.constants.CompanyFieldsID.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@Slf4j
public abstract class CompanyMapper {
    @Autowired
    private EventMatcher eventMatcher;

    @Mapping(target = "customFieldValues", expression = "java(setCustomFields(inputCompany))")
    @Mapping(target = "_embedded", expression = "java(setEmbeddedCompany(inputCompany.getUsrOldEvents()))")
    public abstract CRMCompany mapToOutput(InputCompany inputCompany);

    public abstract List<CRMCompany> mapToOutput(List<InputCompany> inputCompanies);

    protected List<CustomFieldValue> setCustomFields(InputCompany input) {
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

        list.add(new CustomFieldValue(EDM, singleValue(input.isUsrCompanyUseEDM())));

//        if (!input.getUsrOldEvents().isBlank()) {
//            List<Value> events = new ArrayList<>();
//            for (String event : input.getUsrOldEvents().split(" ")) {
//                if (CompanyEvent.contains(event)) {
//                    events.add(new Value(CompanyEvent.of(event)));
//                }
//            }
//            list.add(new CustomFieldValue(EVENTS, events));
//        }

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

    protected EmbeddedCompany setEmbeddedCompany(String events) {

        if (events.isBlank()) return null;
        List<Tag> tags = Arrays.stream(events.split(" "))
                .filter(eventMatcher::correctEvent)
                .map(eventMatcher::parseEvent)
                .map(event -> new Tag(event, null))
                .collect(Collectors.toList());
        return new EmbeddedCompany(tags);
    }
}
