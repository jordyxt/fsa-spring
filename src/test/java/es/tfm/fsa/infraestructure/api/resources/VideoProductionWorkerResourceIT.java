package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.domain.model.VideoProductionWorker;
import es.tfm.fsa.infraestructure.api.RestClientTestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static es.tfm.fsa.infraestructure.api.resources.VideoProductionWorkerResource.WORKERS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RestTestConfig
public class VideoProductionWorkerResourceIT {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testCreate() {
        VideoProductionWorker videoProductionWorker = VideoProductionWorker.builder().name("actorRTest1").
                description("description").build();
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(WORKERS).body(Mono.just(videoProductionWorker), VideoProductionWorker.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(VideoProductionWorker.class)
                .value(Assertions::assertNotNull)
                .value(returnVideoProductionWorker -> {
                    System.out.println(">>>>> Test:: returnVideoProductionWorker:" + returnVideoProductionWorker);
                    assertEquals("actorRTest1", returnVideoProductionWorker.getName());
                    assertEquals("description", returnVideoProductionWorker.getDescription());
                });
    }

    @Test
    void testUpdate() {
        VideoProductionWorker videoProductionWorker = VideoProductionWorker.builder().name("actorRTest2").
                description("description").build();
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(WORKERS).body(Mono.just(videoProductionWorker), VideoProductionWorker.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(VideoProductionWorker.class)
                .value(Assertions::assertNotNull)
                .value(returnVideoProductionWorker -> {
                    VideoProductionWorker videoProductionWorkerUpdated = VideoProductionWorker.builder().name("actorRTest32").description("description").build();
                    this.restClientTestService.loginAdmin(webTestClient)
                            .put()
                            .uri(WORKERS + "/" + returnVideoProductionWorker.getId())
                            .body(Mono.just(videoProductionWorkerUpdated), VideoProductionWorker.class)
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody(VideoProductionWorker.class)
                            .value(Assertions::assertNotNull)
                            .value(returnVideoProductionWorkerUpdated -> {
                                System.out.println(">>>>> Test:: returnVideoProductionWorker:" + returnVideoProductionWorkerUpdated);
                                assertEquals("actorRTest32", returnVideoProductionWorkerUpdated.getName());
                                assertEquals("description", returnVideoProductionWorkerUpdated.getDescription());
                            });
                });
    }

    @Test
    void testDelete() {
        VideoProductionWorker videoProductionWorker = VideoProductionWorker.builder().name("actorRTest3").description("description").build();
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(WORKERS).body(Mono.just(videoProductionWorker), VideoProductionWorker.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(VideoProductionWorker.class)
                .value(Assertions::assertNotNull)
                .value(returnVideoProductionWorker -> {
                    this.restClientTestService.loginAdmin(webTestClient)
                            .delete()
                            .uri(WORKERS + "/" + returnVideoProductionWorker.getId())
                            .exchange()
                            .expectStatus().isNoContent();
                    this.restClientTestService.loginAdmin(webTestClient)
                            .get()
                            .uri(WORKERS + "/" + returnVideoProductionWorker.getId())
                            .exchange()
                            .expectStatus().isNotFound();
                });
    }
}
