package com.example.dbeaver_migration_mappers.input_models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InputContact {
    private String id;
    private String name; // contact -> name
    private String jobTitle; // contact -> jobTitle
    private String company; // accountId -> name
    private String phone; // contact -> phone
    @JsonProperty("mobile_phone")
    private String mobilePhone; // contact -> mobilePhone
    private String email; // contact -> email
    @JsonProperty("alternative_email")
    private String alternativeEmail; // contact -> usrAdvancedEmail
    private String type; // TypeId (ContactType.java) -> name
    private String dear; // SalutationTypeId (ContactSalutationType.java) -> dear || dear || usrDear;
    private String io; // Contact -> usrIO
    private String role; // usrDecisionRoleId -> name
    private String department; // departmentId (Department.java) -> name
    @JsonProperty("url_old_events")
    private String usrOldEvents; // contact -> usrOldEvents
    private String usrDiscCard; // contact -> UsrDiscCard
    private String moderation; // usrModerationId (UsrModeration.java) -> name
    private byte doNotUseEmail;
    private byte doNotUseCall;
    private byte doNotUseFax;
    private byte doNotUseSms;
    private byte doNotUsEmail;
    private String usrPrimKontakta; // contact -> usrPrimKontakta // notes


}
