package com.github.alexpumpkin.stackoverflowdemo1.services;

import com.github.alexpumpkin.stackoverflowdemo1.model.Resource;
import reactor.core.publisher.Mono;

/**
 * Repository to save the given resource.
 */
public interface Repository {
    Mono<Resource> save(Resource resource);
}
