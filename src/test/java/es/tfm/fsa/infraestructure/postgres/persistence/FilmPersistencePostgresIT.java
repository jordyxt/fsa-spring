package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.TestConfig;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.infraestructure.api.dtos.FilmFormDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestConfig
public class FilmPersistencePostgresIT {
    @Autowired
    private FilmPersistencePostgres filmPersistencePostgres;
    @Test
    void testFindByTitleNullSafe() {
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
    @Test
    void testCreate() {
        StepVerifier
                .create(Mono.justOrEmpty(this.filmPersistencePostgres.create(
                        FilmFormDto.BBuilder().title("filmTitleP1").description("descriptionP").
                                releaseDate(LocalDate.of(2022, Month.JANUARY,1)).
                                genreList(Arrays.asList("action","adventure","sci-fi")).
                                directorList(Arrays.asList()).actorList(Arrays.asList())
                        .build())))
                .expectNextMatches(film -> {
                    assertEquals("filmTitleP1", film.getTitle());
                    assertEquals("descriptionP", film.getDescription());
                    return true;
                })
                .verifyComplete();
    }
}
