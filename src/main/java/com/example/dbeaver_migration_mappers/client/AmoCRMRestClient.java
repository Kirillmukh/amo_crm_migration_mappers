package com.example.dbeaver_migration_mappers.client;

import com.example.dbeaver_migration_mappers.client.crm.EntityType;
import com.example.dbeaver_migration_mappers.crm_models.request.*;
import com.example.dbeaver_migration_mappers.crm_models.entity.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.response.*;
import com.example.dbeaver_migration_mappers.crm_models.util.ResponseTag;
import com.example.dbeaver_migration_mappers.crm_models.util.Tag;

import java.util.List;

public interface AmoCRMRestClient { // TODO: 31.12.2024 проверить, что return type тот. Может быть заменить response и wrapper на List<>
    CRMCompany getCompany(int id);
    ResponseTag createTags(List<Tag> tags, EntityType entityType);
    ResponseTag getTags(EntityType et, int page);
    CRMComplexLeadResponseWrapper createComplexLead(CRMLeadRequest crmLeadRequest);
    CRMLeadResponse createLead(CRMLeadRequest crmLeadRequest);
    CRMContactResponse createContact(CRMContactRequest crmContactRequest);
    CRMToEntityResponse linkLead(int leadId, CRMToEntityRequest crmToEntityRequest);
    CRMToEntityLinksResponse linkLeads(CRMToEntityLinksRequest crmToEntityLinksRequest);
}
