package com.example.dbeaver_migration_mappers.input_models.hateoas;

public record HateoasEntity<T> (Iterable<Link> links, T content) { }