package com.github.alexpumpkin.stackoverflowdemo1.services;

import com.github.alexpumpkin.stackoverflowdemo1.model.Resource;
import reactor.core.publisher.Mono;

/**
 * Processor for long running asynchronous tasks
 */
public interface Processor {
    Mono<Void> run(Resource resource);
}
