package com.example.dbeaver_migration_mappers.output_models.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CustomFieldValue {
    private int fieldId;
    private String fieldName;
    private String fieldCode = null;
    private FieldType fieldType = FieldType.text;
    private List<Value> values;

    @NoArgsConstructor
    static class Value {
        @Getter
        @Setter
        private String value;
    }
    enum FieldType {
        numeric, textarea, text, select, multitext
    }
}
