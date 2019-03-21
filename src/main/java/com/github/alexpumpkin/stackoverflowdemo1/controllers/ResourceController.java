package com.github.alexpumpkin.stackoverflowdemo1.controllers;

import com.github.alexpumpkin.stackoverflowdemo1.model.Resource;
import com.github.alexpumpkin.stackoverflowdemo1.services.Processor;
import com.github.alexpumpkin.stackoverflowdemo1.services.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ResourceController {
    private final Repository repository;
    private final Processor processor;

    @Autowired
    public ResourceController(Repository repository, Processor processor) {
        this.repository = repository;
        this.processor = processor;
    }

    @PostMapping("/create")
    public Mono<Resource> create(@RequestBody Resource resource) {
        processor.run(resource).subscribe();
        return repository.save(resource);
    }
}
