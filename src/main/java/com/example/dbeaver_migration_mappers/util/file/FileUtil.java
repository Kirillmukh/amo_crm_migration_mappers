package com.example.dbeaver_migration_mappers.util.file;

import com.example.dbeaver_migration_mappers.util.file.exception.FileCreationException;
import com.example.dbeaver_migration_mappers.util.file.exception.FileReadingException;
import com.example.dbeaver_migration_mappers.util.file.exception.FileWritingException;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Component
@Slf4j
public class FileUtil {
    @Getter
    private final String path;
    @Getter
    private boolean isCreatedFile;

    public FileUtil(String path, String filename) {
        this.path = Path.of(path, filename).toString().replace("%20", " ");
    }

    @PostConstruct
    public void init() throws FileCreationException {
        isCreatedFile = createIfNotExists();
    }

    public boolean createIfNotExists() throws FileCreationException {
        File file = new File(path);
        if (file.exists()) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            throw new FileCreationException(e);
        }
    }

    public void write(String data) throws FileWritingException {
        synchronized (FileUtil.class) {
            Path file = Path.of(path);
            ByteBuffer byteBuffer = ByteBuffer.wrap(data.getBytes());
            try (FileChannel fileChannel = FileChannel.open(file, StandardOpenOption.WRITE)) {
                fileChannel.write(byteBuffer);
            } catch (IOException e) {
                throw new FileWritingException(e);
            }
        }
    }

    public String readFile() throws FileReadingException {
        synchronized (FileUtil.class) {
            Path file = Path.of(path);
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.toFile().length());
            try (FileChannel fileChannel = FileChannel.open(file, StandardOpenOption.READ)) {
                fileChannel.read(byteBuffer);
            } catch (IOException e) {
                throw new FileReadingException(e);
            }
            return new String(byteBuffer.array());
        }
    }
}
