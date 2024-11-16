package com.example.dbeaver_migration_mappers.crm_models.util;

import java.util.List;

public record CustomFieldValue (int fieldId, List<Value> values) {
}
