package com.example.dbeaver_migration_mappers.util;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Component
public class MapThreadExecutor {
    private final ExecutorService executorService;

    public MapThreadExecutor() {
        this.executorService = Executors.newSingleThreadExecutor();
    }

    private String data;

    public void setData(String data) {
        synchronized (MapThreadExecutor.class) {
            this.data = data;
        }
    }
    public void update(Consumer<String> runnable) {
        synchronized (MapThreadExecutor.class) {
            if (data != null) {
                executorService.execute(() -> runnable.accept(data));
                data = null;
            }
        }
    }
    @PreDestroy
    public void onDelete() {
        executorService.close();
    }
}
