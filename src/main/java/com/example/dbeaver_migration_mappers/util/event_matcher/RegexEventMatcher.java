package com.example.dbeaver_migration_mappers.util.event_matcher;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Primary
public class RegexEventMatcher implements EventMatcher {
    private static final Pattern pattern = Pattern.compile("([a-zA-Zа-яА-Я]{2,4}|\\w{2,4})\\d{2}/?\\d?([а-яa-z]+)?");

    @Override
    public boolean correctEvent(String event) {
        Matcher matcher = pattern.matcher(event);
        return matcher.find();
    }

    @Override
    public String parseEvent(String event) {
        Matcher matcher = pattern.matcher(event);
        if (!matcher.find()) {
            throw new IllegalArgumentException("pattern didn't match. Call correctEvent() before parseEvent()");
        }
        return matcher.group();
    }
}
