package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.TestConfig;
import es.tfm.fsa.domain.model.Rating;
import es.tfm.fsa.infraestructure.api.dtos.FilmFormDto;
import es.tfm.fsa.infraestructure.api.dtos.RatingFormDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfig
public class RatingPersistencePostgresIT {
    @Autowired
    private FilmPersistencePostgres filmPersistencePostgres;
    @Autowired
    private RatingPersistencePostgres ratingPersistencePostgres;
    @Test
    void testCreate() {
        StepVerifier
                .create(Mono.justOrEmpty(this.filmPersistencePostgres.create(
                        FilmFormDto.BBuilder().title("rateTitleP1").description("descriptionP").
                                releaseDate(LocalDate.of(2022, Month.JANUARY,1)).
                                genreList(Arrays.asList("action","adventure","sci-fi")).build())))
                .expectNextMatches(film -> {
                    StepVerifier
                            .create(Mono.justOrEmpty(this.ratingPersistencePostgres.create(
                                    RatingFormDto.builder().rating(6).username("admin").
                                            videoProductionId(film.getId()).build())))
                            .expectNextMatches(rating -> {
                                assertEquals(6, rating);
                                return true;
                            })
                            .verifyComplete();
                    return true;
                })
                .verifyComplete();
    }
}
