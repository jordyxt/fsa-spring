package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestConfig
public class FilmPersistencePostgresIT {
    @Autowired
    private FilmPersistencePostgres filmPersistencePostgres;
    @Test
    void testFindBTitleNullSafe() {
        StepVerifier
                .create(Flux.fromStream(this.filmPersistencePostgres.findByTitleNullSafe("Jurassic World Dominion")))
                .expectNextMatches(film -> {
                    assertEquals("Four years after the destruction of Isla Nublar, "+
                            "dinosaurs now live--and hunt--alongside humans all over the world.", film.getDescription());
                    return true;
                })
                .thenCancel()
                .verify();
    }
}
