package com.example.dbeaver_migration_mappers.client;

import com.example.dbeaver_migration_mappers.input_models.hateoas.Link;

import java.util.Optional;

public interface HateoasLinkRestClient<T> {
    Optional<T> request(Link value);
}