package com.example.dbeaver_migration_mappers.crm_models.embedded;

import com.example.dbeaver_migration_mappers.crm_models.entity.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMContact;
import com.example.dbeaver_migration_mappers.crm_models.util.Tag;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EmbeddedLead {
        private List<CRMContact> contacts;
        private List<CRMCompany> companies;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private List<Tag> tags;
}
