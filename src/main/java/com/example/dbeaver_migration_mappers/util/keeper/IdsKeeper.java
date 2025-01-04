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
    private int loadFactor = 100;

    public IdsKeeper(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    public IdsKeeper(FileUtil fileUtil, int loadFactor) {
        this(fileUtil);
        this.loadFactor = loadFactor;
    }
    @Getter
    private List<String> ids = new ArrayList<>();

    public void append() throws FileWritingException {
        String reduce = ids.stream()
                .map(ids -> ids.concat("\n"))
                .reduce("", String::concat);
        fileUtil.append(reduce);
        this.ids.clear();
    }

    public void clear() {
        this.ids.clear();
    }

    public void read() throws FileReadingException {
        this.ids.clear();
        this.ids = Arrays.stream(fileUtil.readFile().split("\n"))
                .collect(Collectors.toList());
    }

    public void offer(String id) throws FileWritingException {
        this.ids.add(id);
        if (this.ids.size() >= loadFactor) {
            append();
        }
    }
    public void delete(List<String> ids) throws FileReadingException, FileWritingException {
        if (!this.ids.isEmpty()) append();
        read();
        this.ids.removeAll(ids);
        String reduce = this.ids.stream()
                .map(id -> id.concat("\n"))
                .reduce("", String::concat);
        fileUtil.write(reduce);
    }
}
