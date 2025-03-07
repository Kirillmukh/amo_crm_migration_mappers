package com.example.dbeaver_migration_mappers.crm_models.request;

import com.example.dbeaver_migration_mappers.crm_models.entity.CRMUpdateContact;

import java.util.List;

public record CRMUpdateContactRequest(List<CRMUpdateContact> list) {
}
