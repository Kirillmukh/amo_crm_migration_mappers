package com.example.dbeaver_migration_mappers.client.collector;

import com.example.dbeaver_migration_mappers.client.HateoasLinkRestClient;
import com.example.dbeaver_migration_mappers.input_models.hateoas.Link;
import com.example.dbeaver_migration_mappers.input_models.hateoas.ListHateoasEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.internal.util.Producer;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class RequestCollector<Entity> {
    private final HateoasLinkRestClient<ListHateoasEntity<Entity>> hateoasLinkRestClient;
    private final Producer<Optional<ListHateoasEntity<Entity>>> entityRestClient;
    public List<Entity> requestContent() {
        Optional<ListHateoasEntity<Entity>> request = entityRestClient.call();
        if (request.isEmpty()) {
            log.error("empty result from entityRestClient.request() in RequestCollector.java, entityRestClient = {}", entityRestClient);
            throw new RuntimeException("empty result from entityRestClient.request() in RequestCollector.java");
        }

        ListHateoasEntity<Entity> databaseRequest = request.get();
        List<Entity> content = databaseRequest.content();
        Link linkToNext = null;

        for (var link : databaseRequest.links()) {
            if (link.rel().equals("next")) {
                linkToNext = link;
                break;
            }
        }

        if (linkToNext == null) {
            return content;
        }
        content.addAll(requestContentHelper(linkToNext));
        return content;
    }

    private List<Entity> requestContentHelper(Link linkToNext) {
        Optional<ListHateoasEntity<Entity>> optionalRequest = hateoasLinkRestClient.request(linkToNext);
        log.info("requestContentHelper: {}", optionalRequest);
        if (optionalRequest.isEmpty()) {
            log.error("empty result from leadDatabaseRestClient.request() in DefaultApplicationFacade.java");
            throw new RuntimeException("empty result from leadDatabaseRestClient.request() in DefaultApplicationFacade.java");
        }

        ListHateoasEntity<Entity> request = optionalRequest.get();

        List<Entity> result = request.content();
        linkToNext = null;

        for (var link : request.links()) {
            if (link.rel().equals("next")) {
                linkToNext = link;
                break;
            }
        }

        if (linkToNext == null) {
            return result;
        }
        result.addAll(requestContentHelper(linkToNext));
        return result;
    }
}
