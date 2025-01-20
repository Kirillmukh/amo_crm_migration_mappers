package com.example.dbeaver_migration_mappers.crm_models.entity;

import com.example.dbeaver_migration_mappers.crm_models.util.CustomFieldValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CRMContact {
    private String name;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("custom_fields_values")
    private List<CustomFieldValue> customFieldValues;
}
