package com.example.dbeaver_migration_mappers.crm_models.entity;

import com.example.dbeaver_migration_mappers.crm_models.util.CustomFieldValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CRMUpdateContact {
    public CRMUpdateContact(int id, CRMContact crmContact) {
        this.id = id;
        this.name = crmContact.getName();
        this.firstName = crmContact.getFirstName();
        this.customFieldValues = crmContact.getCustomFieldValues();
    }
    private int id;
    private String name;
    @JsonProperty("created_at")
    private long createdAt;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("custom_fields_values")
    private List<CustomFieldValue> customFieldValues;

}
