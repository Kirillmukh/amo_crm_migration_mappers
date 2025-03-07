package com.example.dbeaver_migration_mappers.input_models.request;


import com.example.dbeaver_migration_mappers.input_models.InputCompany;
import com.example.dbeaver_migration_mappers.input_models.InputContact;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RequestContactAndCompany {
    private InputContact contact;
    private InputCompany company;
}
