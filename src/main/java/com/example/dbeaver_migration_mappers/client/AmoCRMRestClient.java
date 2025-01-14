package com.example.dbeaver_migration_mappers.client;

import com.example.dbeaver_migration_mappers.crm_models.request.CRMContactRequest;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMLeadRequest;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMToEntityLinksRequest;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMToEntityRequest;
import com.example.dbeaver_migration_mappers.crm_models.response.*;

import java.util.List;

public interface AmoCRMRestClient { // TODO: 31.12.2024 проверить, что return type тот. Может быть заменить response и wrapper на List<>
    List<CRMComplexLeadResponse> createComplexLead(CRMLeadRequest crmLeadRequest);
    CRMLeadResponse createLead(CRMLeadRequest crmLeadRequest);
    CRMContactResponse createContact(CRMContactRequest crmContactRequest);
    CRMToEntityResponse linkLead(int leadId, CRMToEntityRequest crmToEntityRequest);
    CRMToEntityLinksResponse linkLeads(CRMToEntityLinksRequest crmToEntityLinksRequest);
}
