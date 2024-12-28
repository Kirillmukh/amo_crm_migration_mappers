package com.example.dbeaver_migration_mappers.crm_models.request;

import com.example.dbeaver_migration_mappers.crm_models.embedded.EmbeddedLead;
import com.example.dbeaver_migration_mappers.crm_models.util.CustomFieldValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CRMLead {
    private String name;
    private int price;
    @JsonProperty("custom_fields_values")
    private List<CustomFieldValue> customFieldValues;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("_embedded")
    private EmbeddedLead embeddedLead;
}
