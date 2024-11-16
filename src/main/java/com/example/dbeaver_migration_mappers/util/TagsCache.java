package com.example.dbeaver_migration_mappers.util;

import com.example.dbeaver_migration_mappers.client.AmoCRMRestClient;
import com.example.dbeaver_migration_mappers.client.EntityType;
import com.example.dbeaver_migration_mappers.crm_models.util.ResponseLinks;
import com.example.dbeaver_migration_mappers.crm_models.util.ResponseTag;
import com.example.dbeaver_migration_mappers.crm_models.util.Tag;
import com.example.dbeaver_migration_mappers.util.file.FileUtil;
import com.example.dbeaver_migration_mappers.util.file.exception.FileReadingException;
import com.example.dbeaver_migration_mappers.util.file.exception.FileWritingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class TagsCache {
    private final FileUtil fileUtil;
    private final AmoCRMRestClient amoCRMRestClient;
    private final ObjectMapper mapper;
    private final Map<String, Integer> map = new HashMap<>();
    private final StringThreadExecutor stringThreadExecutor;
    @PostConstruct
    void init() throws JsonProcessingException, FileWritingException, FileReadingException {
        if (fileUtil.isCreatedFile()) {
            List<Tag> tagsList = new ArrayList<>();
            ResponseLinks responseLinks;
            ResponseTag request;
            int page = 1;
            do {
                request = amoCRMRestClient.getTags(EntityType.COMPANY, page++);
                tagsList.addAll(request._embedded().tags());
                responseLinks = request.links();
            } while (responseLinks.next() != null);
            log.info(tagsList.toString());
            Map<String, Integer> collect = tagsList.stream().filter(t -> !t.name().startsWith("C&amp;B10")).collect(Collectors.toMap(Tag::name, Tag::id));
            map.putAll(collect);
            fileUtil.write(mapper.writeValueAsString(map));
        } else {
            map.putAll(mapper.readValue(fileUtil.readFile(), map.getClass()));
        }
    }
    public List<Tag> getTags(List<Tag> tags) {
        List<Tag> result = new ArrayList<>(tags.size());
        List<Tag> tagsToCreate = new ArrayList<>();
        for (Tag tag : tags) {
            if (map.containsKey(tag.name())) {
                result.add(new Tag(tag.name(), map.get(tag.name())));
            } else {
                tagsToCreate.add(tag);
            }
        }
        if (tagsToCreate.size() > 1) {
            tagsToCreate = amoCRMRestClient.createTags(tagsToCreate, EntityType.COMPANY)._embedded().tags();
            this.map.putAll(tagsToCreate.stream().collect(Collectors.toMap(Tag::name, Tag::id)));
            result.addAll(tagsToCreate);

            try {
                stringThreadExecutor.setData(mapper.writeValueAsString(map));
                stringThreadExecutor.update(string -> {
                    try {
                        fileUtil.write(string);
                    } catch (FileWritingException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }
        return result;
    }
}
