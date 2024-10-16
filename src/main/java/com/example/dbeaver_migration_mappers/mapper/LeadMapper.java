package com.example.dbeaver_migration_mappers.mapper;

import com.example.dbeaver_migration_mappers.input_models.InputLead;
import com.example.dbeaver_migration_mappers.output_models.OutputLead;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LeadMapper {

    @Mapping(target = "name", source = "opportunity")
    @Mapping(target = "price", source = "budget")
    OutputLead mapToOutput(InputLead inputLead);

    @Getter
    enum Category {
        OUR_TEAM("0 Наши участники", 1491147), _2, _3, _4, _5;
        private String value;
        private int code;
        Category(String value, int code) {
            this.value = value;
            this.code = code;
        }
        // from
        static final int ID = 2922069;
    }
}
