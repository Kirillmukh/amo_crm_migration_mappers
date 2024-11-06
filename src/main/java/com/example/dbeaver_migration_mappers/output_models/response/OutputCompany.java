package com.example.dbeaver_migration_mappers.output_models.response;

import com.example.dbeaver_migration_mappers.output_models.embedded.EmbeddedCompany;
import com.example.dbeaver_migration_mappers.output_models.util.CustomFieldValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class OutputCompany {
    private String name;
    @JsonProperty("custom_fields_values")
    private List<CustomFieldValue> customFieldValues;
    private EmbeddedCompany _embedded;
}
