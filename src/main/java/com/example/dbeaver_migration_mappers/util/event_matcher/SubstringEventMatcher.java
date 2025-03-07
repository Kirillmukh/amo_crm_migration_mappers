package com.example.dbeaver_migration_mappers.util.event_matcher;

import org.springframework.stereotype.Component;

@Component
public class SubstringEventMatcher implements EventMatcher {
    @Override
    public boolean correctEvent(String event) {
        switch (event) {
            case "19.06)":
            case "только":
                return false;
            default:
                return true;
        }
    }
    @Override
    public String parseEvent(String event) {
        int index = event.indexOf("(");
        if (index != -1) {
            event = event.substring(0, index);
        }
        return event;
    }
}
