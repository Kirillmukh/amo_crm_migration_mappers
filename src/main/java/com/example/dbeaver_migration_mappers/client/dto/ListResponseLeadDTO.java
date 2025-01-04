package com.example.dbeaver_migration_mappers.client.dto;

import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import com.example.dbeaver_migration_mappers.input_models.request.RequestLead;
import lombok.Getter;

public class ListResponseLeadDTO {
    @Getter
    private ListHateoasEntity<RequestLead> entity;
}
