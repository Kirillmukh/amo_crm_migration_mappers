package com.example.dbeaver_migration_mappers.crm_models.request;

import com.example.dbeaver_migration_mappers.crm_models.entity.CRMContact;

import java.util.List;

public record CRMContactRequest(List<CRMContact> crmContactList) {

}
