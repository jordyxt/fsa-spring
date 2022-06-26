package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.infraestructure.api.RestClientTestService;
import es.tfm.fsa.infraestructure.api.dtos.FilmFormDto;
import es.tfm.fsa.infraestructure.api.dtos.RatingFormDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static es.tfm.fsa.infraestructure.api.resources.FilmResource.FILMS;
import static es.tfm.fsa.infraestructure.api.resources.RatingResource.RATINGS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RestTestConfig
public class RatingResourceIT {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testCreate() {
        FilmFormDto filmFormDto = FilmFormDto.BBuilder().title("rateRTest1").description("description").
                releaseDate(LocalDate.of(2022, Month.JANUARY, 1)).
                genreList(Arrays.asList("action", "adventure", "sci-fi")).build();
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(FILMS).body(Mono.just(filmFormDto), FilmFormDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Film.class)
                .value(Assertions::assertNotNull)
                .value(returnFilm -> {
                    RatingFormDto ratingFormDto = RatingFormDto.builder().rating(9).
                            videoProductionId(returnFilm.getId()).build();
                    this.restClientTestService.loginBasic(webTestClient)
                            .post()
                            .uri(RATINGS).body(Mono.just(ratingFormDto), RatingFormDto.class)
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody(Integer.class)
                            .value(Assertions::assertNotNull)
                            .value(returnRating -> {
                                System.out.println(">>>>> Test:: returnRating:" + returnRating);
                                assertEquals(9, returnRating);
                            });
                });

    }
}
