package com.example.dbeaver_migration_mappers.client.dto;

import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestContactWithoutCompanyDTO;
import lombok.Getter;

public class ListResponseContactsWithoutContactDTO {
    @Getter
    private ListHateoasEntity<RequestContactWithoutCompanyDTO> entity;
}
