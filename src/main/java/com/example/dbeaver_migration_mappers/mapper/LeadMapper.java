package com.example.dbeaver_migration_mappers.mapper;

import com.example.dbeaver_migration_mappers.input_models.InputLead;
import com.example.dbeaver_migration_mappers.output_models.response.OutputLead;
import com.example.dbeaver_migration_mappers.output_models.constants.LeadFieldsID;
import com.example.dbeaver_migration_mappers.output_models.util.CustomFieldValue;
import com.example.dbeaver_migration_mappers.output_models.util.Value;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LeadMapper {

    @Mapping(target = "name", source = "opportunity")
    @Mapping(target = "price", source = "budget")
    @Mapping(target = "customFieldValues", expression = "java(setCustomFields(inputLead))")
    OutputLead mapToOutput(InputLead inputLead);

    default List<CustomFieldValue> setCustomFields(InputLead inputLead) {
        List<CustomFieldValue> list = new ArrayList<>();
        list.add(new CustomFieldValue(LeadFieldsID.COMMENTARY, List.of(createValue(inputLead.getCommentary()))));
        list.add(new CustomFieldValue(LeadFieldsID.LEAD_SOURCE, List.of(createValue(inputLead.getLeadSource()))));
        list.add(new CustomFieldValue(LeadFieldsID.FORUM, List.of(createValue(inputLead.getConference()))));
        return list;
    }
    private Value createValue(String value) {
        return new Value(value, null);
    }
}
