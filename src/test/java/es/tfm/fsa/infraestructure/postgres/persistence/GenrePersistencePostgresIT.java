package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.TestConfig;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.persistence.GenrePersistence;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfig
public class GenrePersistencePostgresIT {
    @Autowired
    private GenrePersistence genrePersistence;
    @Test
    void testCreate() {
        StepVerifier
                .create(Mono.justOrEmpty(this.genrePersistence.create(
                        Genre.builder().name("nameP1").description("descriptionP").build())))
                .expectNextMatches(genre -> {
                    assertEquals("nameP1", genre.getName());
                    assertEquals("descriptionP", genre.getDescription());
                    return true;
                })
                .verifyComplete();
    }
}
