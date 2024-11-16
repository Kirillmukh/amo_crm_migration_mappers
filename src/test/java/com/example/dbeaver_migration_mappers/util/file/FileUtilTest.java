package com.example.dbeaver_migration_mappers.util.file;

import com.example.dbeaver_migration_mappers.util.file.exception.FileCreationException;
import com.example.dbeaver_migration_mappers.util.file.exception.FileReadingException;
import com.example.dbeaver_migration_mappers.util.file.exception.FileWritingException;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {
    private static final String sourceDir = "";
    private static final String testFile = "test.txt";
    private static final String data = "Hello world!";
    private static FileUtil defaultFileUtil;
    private static FileUtil wrongFileUtil;
    private static File getFile() {
        Path path = Path.of(sourceDir, testFile);
        return path.toFile();
    }
    @BeforeAll
    public static void setUp() {
        defaultFileUtil = new FileUtil(sourceDir, testFile);
        wrongFileUtil = new FileUtil("/hello", "");
    }
    @BeforeEach
    public void setFileUtil() throws IOException {
        getFile().createNewFile();
    }
    @AfterAll
    public static void deleteFile() {
        getFile().delete();
    }
    @Test
    void createIfNotExists_createFile_shouldCreate() {
        File file = getFile();
        if (file.exists()) {
            file.delete();
        }
        boolean isCreated = false;

        try {
            isCreated = defaultFileUtil.createIfNotExists();
        } catch (FileCreationException e) {
            System.out.println("FileCreationException: " + e.getMessage());
        }

        assertTrue(isCreated, "FileUtil didn't create file");
        assertTrue(new File(testFile).exists(), "File not created");
    }
    @Test
    void createIfNotExists_wrongPath_shouldThrowException() {
        assertThrows(FileCreationException.class, wrongFileUtil::createIfNotExists);
    }
    @Test
    void createIfNotExists_alreadyCreated_returningFalse() {
        boolean isCreated = true;

        try {
            isCreated = defaultFileUtil.createIfNotExists();
        } catch (FileCreationException e) {
            throw new RuntimeException(e);
        }

        assertFalse(isCreated);
    }
    @Test
    void write_correctInput_void() throws IOException {
        try {
            defaultFileUtil.write(data);
        } catch (FileWritingException e) {
            System.out.println("FileWritingException: " + e.getMessage());
        }

        assertEquals(Files.readString(Path.of(testFile)), data);
    }
    @Test
    void write_wrongPath_throwException() {
        assertThrows(FileWritingException.class, () -> wrongFileUtil.write(data));
    }
    @Test
    void readFile() throws FileReadingException, IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))){
            writer.write(data);
        }

        assertEquals(data, defaultFileUtil.readFile());
    }
    @Test
    void readFile_wrongPath_throwException() {
        assertThrows(FileReadingException.class, wrongFileUtil::readFile);
    }
}