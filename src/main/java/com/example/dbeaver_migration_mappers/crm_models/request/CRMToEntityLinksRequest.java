package com.example.dbeaver_migration_mappers.crm_models.request;

import com.example.dbeaver_migration_mappers.crm_models.entity.CRMToEntityLinks;

import java.util.List;

public record CRMToEntityLinksRequest(List<CRMToEntityLinks> linksRequestList) {
}
