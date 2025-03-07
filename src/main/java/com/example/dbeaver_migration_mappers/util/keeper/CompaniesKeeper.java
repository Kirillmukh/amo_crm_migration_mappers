package com.example.dbeaver_migration_mappers.util.keeper;

import com.example.dbeaver_migration_mappers.util.file.FileUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CompaniesKeeper extends IdsKeeper {
    public CompaniesKeeper(@Qualifier("savedCompanies") FileUtil fileUtil) {
        super(fileUtil);
    }
}
