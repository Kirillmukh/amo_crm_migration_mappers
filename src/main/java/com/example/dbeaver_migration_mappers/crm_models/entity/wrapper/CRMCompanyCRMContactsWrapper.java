package com.example.dbeaver_migration_mappers.crm_models.entity.wrapper;

import com.example.dbeaver_migration_mappers.crm_models.entity.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMContact;

import java.util.List;

public record CRMCompanyCRMContactsWrapper(
        CRMCompany crmCompany,
        List<CRMContact> crmContact
) {
}
