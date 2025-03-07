package com.example.dbeaver_migration_mappers.crm_models.entity.wrapper;

import com.example.dbeaver_migration_mappers.crm_models.entity.CRMContact;
import com.example.dbeaver_migration_mappers.input_models.InputCompany;

import java.util.List;

public record InputCompaniesCRMContactsWrapper(List<InputCompany> inputCompanies, List<List<CRMContact>> contacts) {
}
