package com.example.dbeaver_migration_mappers.input_models.hateoas;

import java.util.List;

public record ListHateoasEntity<T> (Iterable<Link> links, List<T> content) { }