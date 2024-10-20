package com.example.dbeaver_migration_mappers.output_models.util;

import java.util.List;

public record CustomFieldValue (int fieldId, List<Value> values) {
}
