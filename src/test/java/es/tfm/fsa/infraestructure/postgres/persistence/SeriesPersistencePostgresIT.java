package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfig
public class SeriesPersistencePostgresIT {
    @Autowired
    private SeriesPersistencePostgres seriesPersistencePostgres;

    @Test
    void testFindBTitleNullSafe() {
        StepVerifier
                .create(Flux.fromStream(this.seriesPersistencePostgres.findByTitleNullSafe("How I Met Your Mother")))
                .expectNextMatches(series -> {
                    assertEquals("A father recounts to his children - through a series of flashbacks - the " +
                            "journey he and his four best friends took leading up to him meeting their mother.", series.getDescription());
                    return true;
                })
                .thenCancel()
                .verify();
    }
}
