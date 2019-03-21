package com.github.alexpumpkin.stackoverflowdemo1;

import com.github.alexpumpkin.stackoverflowdemo1.model.Resource;
import com.github.alexpumpkin.stackoverflowdemo1.services.Processor;
import com.github.alexpumpkin.stackoverflowdemo1.services.Repository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebFluxTest
public class StackoverflowDemo1ApplicationTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(StackoverflowDemo1ApplicationTests.class);
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private Repository repository;
    @MockBean
    private Processor processor;

    @Test
    public void testController() {
        AtomicBoolean processed = new AtomicBoolean(false);

        given(repository.save(any(Resource.class)))
                .will(invocation -> Mono.fromCallable(() -> invocation.getArgument(0))
                        .log());
        given(processor.run(any(Resource.class)))
                .will(invocation -> Mono.fromRunnable(() -> processed.set(true))
                        .delaySubscription(Duration.ofSeconds(3))
                        .log());

        webTestClient.post()
                .uri("/create")
                .syncBody(new Resource("name", "data"))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.name").isEqualTo("name")
                .jsonPath("$.data").isEqualTo("data");
        LOGGER.info("Controller response is ok.");

        await().atMost(org.awaitility.Duration.FIVE_SECONDS)
                .untilAtomic(processed, is(true));
    }

}
