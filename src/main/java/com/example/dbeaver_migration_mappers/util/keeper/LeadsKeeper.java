package com.example.dbeaver_migration_mappers.util.keeper;

import com.example.dbeaver_migration_mappers.util.file.FileUtil;
import com.example.dbeaver_migration_mappers.util.file.exception.FileReadingException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class LeadsKeeper extends IdsKeeper {
    public LeadsKeeper(@Qualifier("savedLeads") FileUtil fileUtil) {
        super(fileUtil);
    }
}
