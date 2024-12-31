package com.example.dbeaver_migration_mappers.util.keeper;

import com.example.dbeaver_migration_mappers.util.file.FileUtil;
import com.example.dbeaver_migration_mappers.util.file.exception.FileReadingException;
import com.example.dbeaver_migration_mappers.util.file.exception.FileWritingException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class IdsKeeper {
    private final FileUtil fileUtil;
    public IdsKeeper(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }
    @Getter
    private List<String> ids = new ArrayList<>();
    public void write() throws FileWritingException {
        fileUtil.write(String.join("\n", ids));
    }
    public void init() throws FileReadingException {
        if (!fileUtil.isCreatedFile()) {
            read();
        }
    }
    public void read() throws FileReadingException {
        this.ids.clear();
        this.ids = Arrays.stream(fileUtil.readFile().split("\n"))
                .collect(Collectors.toList());
    }
    public void offer(String id) {
        this.ids.add(id);
    }
    public void delete(String id) {
        this.ids.remove(id);
    }
}
