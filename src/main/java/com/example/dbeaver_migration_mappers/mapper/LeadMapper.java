package com.example.dbeaver_migration_mappers.mapper;

import com.example.dbeaver_migration_mappers.crm_models.embedded.EmbeddedLead;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMCompany;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMContact;
import com.example.dbeaver_migration_mappers.input_models.InputCompany;
import com.example.dbeaver_migration_mappers.input_models.InputContact;
import com.example.dbeaver_migration_mappers.input_models.InputLead;
import com.example.dbeaver_migration_mappers.crm_models.request.CRMLead;
import com.example.dbeaver_migration_mappers.crm_models.util.CustomFieldValue;
import com.example.dbeaver_migration_mappers.crm_models.util.Value;
import com.example.dbeaver_migration_mappers.input_models.request.RequestLead;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.example.dbeaver_migration_mappers.crm_models.constants.LeadFieldsID.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Slf4j
public abstract class LeadMapper {
    @Autowired
    protected ContactMapper contactMapper;
    @Autowired
    protected CompanyMapper companyMapper;

    @Mapping(target = "name", source = "opportunity")
    @Mapping(target = "price", source = "budget")
    @Mapping(target = "customFieldValues", expression = "java(setCustomFields(inputLead))")
    public abstract CRMLead mapInputLead(InputLead inputLead);
    public abstract List<CRMLead> mapInputLead(List<InputLead> inputLeads);
    protected List<CustomFieldValue> setCustomFields(InputLead inputLead) {
        List<CustomFieldValue> list = new ArrayList<>();
        list.add(new CustomFieldValue(COMMENTARY, singleValue(inputLead.getCommentary())));
        list.add(new CustomFieldValue(LEAD_SOURCE, singleValue(inputLead.getLeadSource())));
        list.add(new CustomFieldValue(FORUM, singleValue(inputLead.getConference())));
        return list;
    }
    private List<Value> singleValue(String value) {
        return List.of(new Value(value));
    }

    @Mapping(target = "embeddedLead", expression = "java(setEmbeddedLead(requestLead))")
    public abstract CRMLead mapRequestLead(RequestLead requestLead);
    public abstract List<CRMLead> mapRequestLead(List<RequestLead> requestLeads);
    protected EmbeddedLead setEmbeddedLead(RequestLead requestLead) {
        InputCompany company = requestLead.getCompany();
        List<InputContact> contacts = requestLead.getContacts();

        CRMCompany crmCompany = companyMapper.mapToOutput(company);
        List<CRMContact> crmContacts = contactMapper.mapToOutput(contacts);

        if (crmCompany == null) log.error("LeadMapper.setEmbeddedLead() crmCompany is null -> List.of(null) threw exception");  // TODO: 28.12.2024 delete this row

        return new EmbeddedLead(crmContacts, List.of(crmCompany), new ArrayList<>());
    }
}
