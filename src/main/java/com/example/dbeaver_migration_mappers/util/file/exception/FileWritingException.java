package com.example.dbeaver_migration_mappers.util.file.exception;

public class FileWritingException extends Exception{
    public FileWritingException() {
        super();
    }

    public FileWritingException(String message) {
        super(message);
    }

    public FileWritingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileWritingException(Throwable cause) {
        super(cause);
    }

    protected FileWritingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
