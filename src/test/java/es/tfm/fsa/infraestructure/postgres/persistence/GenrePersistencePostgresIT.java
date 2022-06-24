package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.TestConfig;
import es.tfm.fsa.domain.exceptions.ConflictException;
import es.tfm.fsa.domain.exceptions.NotFoundException;
import es.tfm.fsa.domain.model.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
public class GenrePersistencePostgresIT {
    @Autowired
    private GenrePersistencePostgres genrePersistencePostgres;
    @Test
    void testCreate() {
        StepVerifier
                .create(Mono.justOrEmpty(this.genrePersistencePostgres.create(
                        Genre.builder().name("nameP1").description("descriptionP").build())))
                .expectNextMatches(genre -> {
                    assertEquals("nameP1", genre.getName());
                    assertEquals("descriptionP", genre.getDescription());
                    return true;
                })
                .verifyComplete();
    }
    @Test
    void testFindBNameAndGroupAndDescriptionNullSafe() {
        StepVerifier
                .create(Mono.justOrEmpty(this.genrePersistencePostgres.create(
                        Genre.builder().name("nameP4").description("description").build())))
                .expectNextMatches(genre -> {
                    assertEquals("nameP4", genre.getName());
                    assertEquals("description", genre.getDescription());
                    return true;
                })
                .verifyComplete();
        StepVerifier
                .create(Flux.fromStream(this.genrePersistencePostgres.findByNameAndDescriptionContainingNullSafe("nameP4", "description")))
                .expectNextMatches(genre -> {
                    assertEquals("description", genre.getDescription());
                    return true;
                })
                .thenCancel()
                .verify();
    }
    @Test
    void testUpdate() {
        StepVerifier
                .create(Mono.justOrEmpty(this.genrePersistencePostgres.create(
                        Genre.builder().name("nameP3").description("descriptionP").build())))
                .expectNextMatches(genre -> {
                    assertEquals("nameP3", genre.getName());
                    assertEquals("descriptionP", genre.getDescription());
                    return true;
                })
                .verifyComplete();
        StepVerifier
                .create(Mono.justOrEmpty(this.genrePersistencePostgres.update("nameP3",
                        Genre.builder().name("nameP3").description("descriptionTest").build())))
                .expectNextMatches(genre -> {
                    assertEquals("descriptionTest", genre.getDescription());
                    return true;
                })
                .verifyComplete();
    }
    @Test
    void testUpdateExistingName() {
        assertThrows(ConflictException.class, () ->this.genrePersistencePostgres.update("action",
                Genre.builder().name("romance").description("Romance").build()));
    }
    @Test
    void testReadByName() {
        StepVerifier
                .create(Mono.justOrEmpty(this.genrePersistencePostgres.findByName("action")))
                .expectNextMatches(genre -> {
                    assertEquals("action", genre.getName());
                    assertEquals("Action", genre.getDescription());
                    return true;
                })
                .expectComplete()
                .verify();
    }
    @Test
    void testDelete() {
        StepVerifier
                .create(Mono.justOrEmpty(this.genrePersistencePostgres.create(
                        Genre.builder().name("nameP2").description("descriptionP2").build())))
                .expectNextMatches(genre -> {
                    assertEquals("nameP2", genre.getName());
                    assertEquals("descriptionP2", genre.getDescription());
                    return true;
                })
                .verifyComplete();
        StepVerifier
                .create(Mono.justOrEmpty(this.genrePersistencePostgres.delete("nameP2")))
                .verifyComplete();
        assertThrows(NotFoundException.class, () ->this.genrePersistencePostgres.delete("nameP2"));
    }
}
