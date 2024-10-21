package com.example.dbeaver_migration_mappers.mapper;

import com.example.dbeaver_migration_mappers.input_models.InputLead;
import com.example.dbeaver_migration_mappers.output_models.response.OutputLead;
import com.example.dbeaver_migration_mappers.output_models.util.CustomFieldValue;
import com.example.dbeaver_migration_mappers.output_models.util.Value;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.ArrayList;
import java.util.List;

import static com.example.dbeaver_migration_mappers.output_models.constants.LeadFieldsID.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LeadMapper {

    @Mapping(target = "name", source = "opportunity")
    @Mapping(target = "price", source = "budget")
    @Mapping(target = "customFieldValues", expression = "java(setCustomFields(inputLead))")
    OutputLead mapToOutput(InputLead inputLead);

    default List<CustomFieldValue> setCustomFields(InputLead inputLead) {
        List<CustomFieldValue> list = new ArrayList<>();
        list.add(new CustomFieldValue(COMMENTARY, singleValue(inputLead.getCommentary())));
        list.add(new CustomFieldValue(LEAD_SOURCE, singleValue(inputLead.getLeadSource())));
        list.add(new CustomFieldValue(FORUM, singleValue(inputLead.getConference())));
        return list;
    }
    private List<Value> singleValue(String value) {
        return List.of(new Value(value));
    }
}
