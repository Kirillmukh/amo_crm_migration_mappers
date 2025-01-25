package com.example.dbeaver_migration_mappers.util;

import org.springframework.stereotype.Component;

@Component
public class EventMatcher {
    public boolean correctEvent(String event) {
        switch (event) {
            case "19.06)":
            case "только":
                return false;
            default:
                return true;
        }
    }

    public String parseEvent(String event) {
        int index = event.indexOf("(");
        if (index != -1) {
            event = event.substring(0, index);
        }
        return event;
    }
}
