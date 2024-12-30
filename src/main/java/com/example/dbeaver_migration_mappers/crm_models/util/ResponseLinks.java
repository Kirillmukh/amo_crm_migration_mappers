package com.example.dbeaver_migration_mappers.crm_models.util;

public record ResponseLinks(
        Link self,
        Link next,
        Link first,
        Link prev
) {
    record Link(String href) {}
}
