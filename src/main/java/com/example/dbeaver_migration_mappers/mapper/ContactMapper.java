package com.example.dbeaver_migration_mappers.mapper;

import com.example.dbeaver_migration_mappers.enums.ValueEnum;
import com.example.dbeaver_migration_mappers.enums.contact.*;
import com.example.dbeaver_migration_mappers.input_models.InputContact;
import com.example.dbeaver_migration_mappers.output_models.response.OutputContact;
import com.example.dbeaver_migration_mappers.output_models.util.CustomFieldValue;
import com.example.dbeaver_migration_mappers.output_models.util.Value;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.ArrayList;
import java.util.List;

import static com.example.dbeaver_migration_mappers.output_models.constants.ContactFieldsID.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContactMapper {
    @Mapping(target = "customFieldValues", expression = "java(setCustomFieldValues(inputContact))")
    @Mapping(target = "firstName", source = "name")
    OutputContact mapToOutput(InputContact inputContact);

    default List<CustomFieldValue> setCustomFieldValues(InputContact input) {
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
        if (!input.getEmail().isBlank()) {
            emailList.add(new Value(new ContactEmail(input.getEmail(), ContactEmail.Type.WORK)));
        }
        if (!input.getAlternativeEmail().isBlank()) {
            emailList.add(new Value(new ContactEmail(input.getAlternativeEmail(), ContactEmail.Type.WORK)));
        }
        if (!emailList.isEmpty()) {
            list.add(new CustomFieldValue(EMAIL, emailList));
        }

        list.add(new CustomFieldValue(TYPE, singleValue(ContactType.of(input.getType()))));

        if (!input.getDear().isBlank()) {
            list.add(new CustomFieldValue(DEAR, singleValue(ContactDear.of(input.getDear()))));
        }

        if (!input.getIo().isBlank()) {
            list.add(new CustomFieldValue(IO, singleValue(input.getIo())));
        }

        if (input.getRole() != null) {
            list.add(new CustomFieldValue(ROLE, singleValue(ContactRole.of(input.getRole()))));
        }

        if (input.getDepartment() != null) {
            list.add(new CustomFieldValue(DEPARTMENT, singleValue(ContactDepartment.of(input.getDepartment()))));
        }

        if (!input.getUsrOldEvents().isBlank()) {
            List<Value> events = new ArrayList<>();
            for (String event : input.getUsrOldEvents().split(" ")) {
                if (ContactEvent.contains(event)) {
                    events.add(new Value(ContactEvent.of(event)));
                }
            }
            list.add(new CustomFieldValue(EVENTS, events));
        }

        if (!input.getUsrDiscCard().isBlank()) {
            list.add(new CustomFieldValue(DISC_CARD, singleValue(input.getUsrDiscCard())));
        }

        if (input.getModeration() != null) {
            list.add(new CustomFieldValue(MODERATION, singleValue(input.getModeration())));
        }

        if (!input.getUsrPrimKontakta().isBlank()) {
            list.add(new CustomFieldValue(NOTES, singleValue(input.getUsrPrimKontakta())));
        }

        return list;
    }
    private List<Value> singleValue(String value) {
        return List.of(new Value(value));
    }
    private List<Value> singleValue(ValueEnum value) {
        return List.of(new Value(value));
    }
}
