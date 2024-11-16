package com.example.dbeaver_migration_mappers.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Component
public class StringThreadExecutor {
    private final ExecutorService executorService;

    public StringThreadExecutor() {
        this.executorService = Executors.newSingleThreadExecutor();
    }

    private String data;

    public void setData(String data) {
        synchronized (StringThreadExecutor.class) {
            this.data = data;
        }
    }
    public void update(Consumer<String> runnable) {
        synchronized (StringThreadExecutor.class) {
            if (data != null) {
                executorService.execute(() -> runnable.accept(data));
                data = null;
            }
        }
    }
}
