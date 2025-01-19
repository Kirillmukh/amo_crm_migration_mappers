package com.example.dbeaver_migration_mappers.client.dto;

import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestCompanyWithContactsDTO;
import lombok.Getter;

public class ListResponseCompanyWithContactsDTO {
    @Getter
    private ListHateoasEntity<RequestCompanyWithContactsDTO> entity;
}
