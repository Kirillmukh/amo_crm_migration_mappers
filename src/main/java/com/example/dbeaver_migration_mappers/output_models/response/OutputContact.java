package com.example.dbeaver_migration_mappers.output_models.response;

import com.example.dbeaver_migration_mappers.output_models.util.CustomFieldValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class OutputContact {
    private String name;
    private String firstName;
    private List<CustomFieldValue> customFieldValues;
}
