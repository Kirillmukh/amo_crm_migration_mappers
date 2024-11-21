package com.example.dbeaver_migration_mappers.util.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class StringThreadExecutor {
    private final ExecutorService executorService;
    private final Object LOCK = new Object();

    public StringThreadExecutor() {
        this.executorService = Executors.newSingleThreadExecutor();
    }

    private String data;

    public void setData(String data) {
        synchronized (LOCK) {
            this.data = data;
        }
    }
    public void update(Consumer<String> runnable) {
        synchronized (LOCK) {
            if (data != null) {
                executorService.execute(() -> runnable.accept(data));
                data = null;
            }
        }
    }
}
