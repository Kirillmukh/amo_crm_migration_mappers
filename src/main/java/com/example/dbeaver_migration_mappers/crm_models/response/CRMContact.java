package com.example.dbeaver_migration_mappers.crm_models.response;

import com.example.dbeaver_migration_mappers.crm_models.util.CustomFieldValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CRMContact {
    private String name;
    private String firstName;
    @JsonProperty("custom_fields_values")
    private List<CustomFieldValue> customFieldValues;
}
