package com.example.dbeaver_migration_mappers.crm_models.embedded;

import com.example.dbeaver_migration_mappers.crm_models.util.Tag;

import java.util.List;

public record EmbeddedTags(
        List<Tag> tags
) {
}
