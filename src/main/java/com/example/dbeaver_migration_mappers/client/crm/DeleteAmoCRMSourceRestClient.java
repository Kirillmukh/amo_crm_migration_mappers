package com.example.dbeaver_migration_mappers.client.crm;

import com.example.dbeaver_migration_mappers.client.AmoCRMSourceRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Slf4j
public class DeleteAmoCRMSourceRestClient implements AmoCRMSourceRestClient {
    private final RestClient restClient;
    @Override
    public void deleteCompany(String companyId) {
        deleteEntity(companyId, Entity.COMPANIES);
    }

    @Override
    public void deleteLead(String leadId) {
        deleteEntity(leadId, Entity.LEADS);
    }

    @Override
    public void deleteContact(String contactId) {
        deleteEntity(contactId, Entity.CONTACTS);
    }
    private void deleteEntity(String id, Entity entityEnum) {
        String entity = entityEnum.toString();
        log.info("Request to delete, id = {}, entity = {}", id, entity);
        String requestBody = "ID=" + id;
        var result = restClient.post()
                .uri("ajax/{entity}/multiple/delete/", entity)
                .header("X-Requested-With", "XMLHttpRequest")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(requestBody)
                .retrieve()
                .body(ResponseEntity.class);
        log.info("Response from delete = {}", result);
    }
    record ResponseEntity(String status, String message) {
    }

    private enum Entity {
        COMPANIES,
        CONTACTS,
        LEADS;
        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }
}
