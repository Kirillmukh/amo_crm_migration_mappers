package com.example.dbeaver_migration_mappers.input_models.request;

import com.example.dbeaver_migration_mappers.input_models.InputContact;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestContactWithoutCompanyDTO {
    private InputContact contact;
}
