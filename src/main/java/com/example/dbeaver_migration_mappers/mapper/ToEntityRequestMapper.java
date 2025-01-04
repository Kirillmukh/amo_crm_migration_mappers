package com.example.dbeaver_migration_mappers.mapper;

import com.example.dbeaver_migration_mappers.crm_models.entity.CRMToEntity;
import com.example.dbeaver_migration_mappers.crm_models.response.CRMContactResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ToEntityRequestMapper {

    @Mapping(target = "toEntityId", source = "contact.id")
    @Mapping(target = "toEntityType", constant = "contacts")
    CRMToEntity mapContactToLeadLinks(CRMContactResponse.Embedded.Contact contact);

    List<CRMToEntity> mapContactsToLeadLinks(List<CRMContactResponse.Embedded.Contact> crmContactResponse);
}
