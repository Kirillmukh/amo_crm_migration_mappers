package com.example.dbeaver_migration_mappers.client.dto;

import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestContactAndCompany;
import lombok.Getter;

public class ListResponseNewContactsOldCompaniesDTO {
    @Getter
    private ListHateoasEntity<RequestContactAndCompany> entity;
}
