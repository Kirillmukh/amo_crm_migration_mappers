package com.example.dbeaver_migration_mappers.mapper;

import com.example.dbeaver_migration_mappers.crm_models.entity.CRMContact;
import com.example.dbeaver_migration_mappers.crm_models.util.CustomFieldValue;
import com.example.dbeaver_migration_mappers.crm_models.util.Value;
import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import com.example.dbeaver_migration_mappers.enums.contact.*;
import com.example.dbeaver_migration_mappers.input_models.InputContact;
import com.example.dbeaver_migration_mappers.input_models.request.RequestContactWithoutCompanyDTO;
import com.example.dbeaver_migration_mappers.util.event_matcher.EventMatcher;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZoneOffset;
import java.util.*;

import static com.example.dbeaver_migration_mappers.crm_models.constants.ContactFieldsID.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@Slf4j
public abstract class ContactMapper {
    @Autowired
    private EventMatcher eventMatcher;

    @Mapping(target = "customFieldValues", expression = "java(setCustomFieldValues(inputContact))")
    @Mapping(target = "firstName", source = "name")
    public abstract CRMContact mapToOutput(InputContact inputContact);

    public abstract List<CRMContact> mapToOutput(List<InputContact> inputContacts);

    protected List<CustomFieldValue> setCustomFieldValues(InputContact input) {
        List<CustomFieldValue> list = new ArrayList<>();

        if (!input.getJobTitle().isBlank()) {
            list.add(new CustomFieldValue(JOB_TITLE, singleValue(input.getJobTitle())));
        }

        List<Value> phoneList = new ArrayList<>();
        ContactPhone.Type phoneType = ContactPhone.Type.WORK;
        if (!input.getPhone().isBlank()) {
            phoneList.add(new Value(new ContactPhone(input.getPhone(), phoneType)));
            phoneType = ContactPhone.Type.MOBILE;
        }
        if (!input.getMobilePhone().isBlank()) {
            phoneList.add(new Value(new ContactPhone(input.getMobilePhone(), phoneType)));
        }
        if (!phoneList.isEmpty()) {
            list.add(new CustomFieldValue(PHONE, phoneList));
        }

        List<Value> emailList = new ArrayList<>();
        ContactEmail.Type emailType = ContactEmail.Type.WORK;
        if (!input.getEmail().isBlank()) {
            emailList.add(new Value(new ContactEmail(input.getEmail(), emailType)));
            emailType = ContactEmail.Type.PERSONALITY;
        }
        if (!input.getAlternativeEmail().isBlank()) {
            emailList.add(new Value(new ContactEmail(input.getAlternativeEmail(), emailType)));
        }
        if (!emailList.isEmpty()) {
            list.add(new CustomFieldValue(EMAIL, emailList));
        }

        ContactType type = ContactType.of(input.getType());
        list.add(new CustomFieldValue(TYPE, singleValue(type)));

        if (!input.getDear().isBlank()) {
            ContactDear dear = ContactDear.of(input.getDear());
            list.add(new CustomFieldValue(DEAR, singleValue(dear)));
        }

        if (!input.getIo().isBlank()) {
            list.add(new CustomFieldValue(IO, singleValue(input.getIo())));
        }

        if (input.getRole() != null) {
            ContactRole role = ContactRole.of(input.getRole());
            list.add(new CustomFieldValue(ROLE, singleValue(role)));
        }

        if (input.getDepartment() != null) {
            ContactDepartment department = ContactDepartment.of(input.getDepartment());
            list.add(new CustomFieldValue(DEPARTMENT, singleValue(department)));
        }

        if (!input.getUsrOldEvents().isBlank()) {
            List<Value> events = Arrays.stream(input.getUsrOldEvents().split(" "))
                    .filter(eventMatcher::correctEvent)
                    .map(eventMatcher::parseEvent)
                    .filter(event -> {
                        if (ContactEvent.contains(event)) {
                            return true;
                        } else {
                            log.info("Event didn't map: {}", event);
                            return false;
                        }
                    })
                    .map(event -> new Value(ContactEvent.of(event)))
                    .toList();
            list.add(new CustomFieldValue(EVENTS, events));
        }

        if (!input.getUsrDiscCard().isBlank()) {
            list.add(new CustomFieldValue(DISC_CARD, singleValue(input.getUsrDiscCard())));
        }

        if (input.getModeration() != null) {
            ContactModeration moderation = ContactModeration.of(input.getModeration());
            list.add(new CustomFieldValue(MODERATION, singleValue(moderation)));
        }

        if (!input.getUsrPrimKontakta().isBlank()) {
            list.add(new CustomFieldValue(NOTES, singleValue(input.getUsrPrimKontakta())));
        }

        if (input.getUsrDateUpdate() != null) {
            list.add(new CustomFieldValue(ACTUAL_DATE, singleValue(input.getUsrDateUpdate().getTime() / 1000)));
        }

        return list;
    }

    private List<Value> singleValue(String value) {
        return List.of(new Value(value));
    }

    private List<Value> singleValue(ValueEnum value) {
        return List.of(new Value(value));
    }
    private List<Value> singleValue(Object o) { return List.of(new Value(o)); }

    @Mapping(target = "customFieldValues", expression = "java(setCustomFieldValues(request.getContact()))")
    @Mapping(target = "firstName", source = "request.contact.name")
    @Mapping(target = "name", source = "request.contact.name")
    public abstract CRMContact mapToOutputRequestContactWithoutCompany(RequestContactWithoutCompanyDTO request);

    public abstract List<CRMContact> mapToOutputRequestContactWithoutCompany(List<RequestContactWithoutCompanyDTO> request);
}
