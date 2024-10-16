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

    //    private FieldType fieldType = FieldType.text;
    private List<Value> values;

    enum FieldType {
        numeric, textarea, text, select, multitext, url, multiselect;

        //        FieldType(String value, int id) {
//            this.value = value;
//            this.id = id;
//        }
        public String getValue() {
            return value;
        }

        private String value;
        private int id;

        public static FieldType fromValue(String value) {
            for (FieldType type : values()) {
                if (type.getValue().equals(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Неизвестное значение " + value);
        }
    }
}
