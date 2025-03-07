package com.example.dbeaver_migration_mappers.util.event_matcher;

public interface EventMatcher {
    boolean correctEvent(String event);
    String parseEvent(String event);
}
