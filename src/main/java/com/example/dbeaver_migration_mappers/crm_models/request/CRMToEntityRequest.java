package com.example.dbeaver_migration_mappers.crm_models.request;

import com.example.dbeaver_migration_mappers.crm_models.entity.CRMToEntity;

import java.util.List;

public record CRMToEntityRequest(List<CRMToEntity> crmToEntityList) {
}
