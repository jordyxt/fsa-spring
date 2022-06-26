package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.TestConfig;
import es.tfm.fsa.domain.exceptions.NotFoundException;
import es.tfm.fsa.domain.model.VideoProductionWorker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
public class VideoProductionWorkerPostgresIT {
    @Autowired
    private VideoProductionWorkerPersistencePostgres videoProductionWorkerPersistencePostgres;

    @Test
    void testCreate() {
        StepVerifier
                .create(Mono.justOrEmpty(this.videoProductionWorkerPersistencePostgres.create(
                        VideoProductionWorker.builder().name("nameP1").description("descriptionP").build())))
                .expectNextMatches(videoProductionWorker -> {
                    assertEquals("nameP1", videoProductionWorker.getName());
                    assertEquals("descriptionP", videoProductionWorker.getDescription());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void testFindBNameAndGroupAndDescriptionNullSafe() {
        StepVerifier
                .create(Mono.justOrEmpty(this.videoProductionWorkerPersistencePostgres.create(
                        VideoProductionWorker.builder().name("nameP2").description("descriptionP").build())))
                .expectNextMatches(videoProductionWorker -> {
                    assertEquals("nameP2", videoProductionWorker.getName());
                    assertEquals("descriptionP", videoProductionWorker.getDescription());
                    return true;
                })
                .verifyComplete();
        StepVerifier
                .create(Flux.fromStream(this.videoProductionWorkerPersistencePostgres.findByNameContainingNullSafe("nameP2")))
                .expectNextMatches(videoProductionWorker -> {
                    assertEquals("descriptionP", videoProductionWorker.getDescription());
                    return true;
                })
                .thenCancel()
                .verify();
    }

    @Test
    void testUpdate() {
        StepVerifier
                .create(Mono.justOrEmpty(this.videoProductionWorkerPersistencePostgres.create(
                        VideoProductionWorker.builder().name("nameP3").description("descriptionP").build())))
                .expectNextMatches(videoProductionWorker -> {
                    StepVerifier
                            .create(Mono.justOrEmpty(this.videoProductionWorkerPersistencePostgres.update(videoProductionWorker.getId(),
                                    VideoProductionWorker.builder().name("nameP3").description("descriptionTest").build())))
                            .expectNextMatches(videoProductionWorkerUpdated -> {
                                assertEquals("descriptionTest", videoProductionWorkerUpdated.getDescription());
                                return true;
                            })
                            .verifyComplete();
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void testDelete() {
        StepVerifier
                .create(Mono.justOrEmpty(this.videoProductionWorkerPersistencePostgres.create(
                        VideoProductionWorker.builder().name("nameP2").description("descriptionP2").build())))
                .expectNextMatches(videoProductionWorker -> {
                    StepVerifier
                            .create(Mono.justOrEmpty(this.videoProductionWorkerPersistencePostgres.delete(videoProductionWorker.getId())))
                            .verifyComplete();
                    assertThrows(NotFoundException.class, () -> this.videoProductionWorkerPersistencePostgres.delete(videoProductionWorker.getId()));
                    return true;
                })
                .verifyComplete();
    }
}
