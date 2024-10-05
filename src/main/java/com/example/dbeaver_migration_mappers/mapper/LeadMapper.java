package com.example.dbeaver_migration_mappers.mapper;

import com.example.dbeaver_migration_mappers.input_models.InputLead;
import com.example.dbeaver_migration_mappers.output_models.OutputLead;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LeadMapper {

    @Mapping(target = "name", source = "opportunity")
    @Mapping(target = "price", source = "budget")
    OutputLead mapToOutput(InputLead inputLead);
}
