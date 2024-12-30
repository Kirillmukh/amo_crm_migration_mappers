package com.example.dbeaver_migration_mappers.crm_models.util;

import com.example.dbeaver_migration_mappers.crm_models.embedded.EmbeddedTags;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseTag(
        int _page,
        @JsonProperty("_links")
        ResponseLinks links,
        EmbeddedTags _embedded

) {
}
