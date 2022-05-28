package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.TestConfig;
import es.tfm.fsa.domain.model.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
                .create(Flux.fromStream(this.genrePersistencePostgres.findByNameAndDescriptionContainingNullSafe(null, "description")))
                .expectNextMatches(genre -> {
                    assertEquals("description", genre.getDescription());
                    return true;
                })
                .thenCancel()
                .verify();
    }
}
