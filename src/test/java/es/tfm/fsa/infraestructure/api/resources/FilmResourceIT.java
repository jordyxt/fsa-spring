package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.infraestructure.api.RestClientTestService;
import es.tfm.fsa.infraestructure.api.dtos.FilmFormDto;
import es.tfm.fsa.infraestructure.api.dtos.FilmSearchDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static es.tfm.fsa.infraestructure.api.resources.FilmResource.FILMS;
import static es.tfm.fsa.infraestructure.api.resources.FilmResource.SEARCH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RestTestConfig
public class FilmResourceIT {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testSearch() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(FILMS + SEARCH)
                        .queryParam("genreList", "action,adventure,sci-fi")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FilmSearchDto.class)
                .value(Assertions::assertNotNull)
                .value(films -> assertTrue(films.stream()
                        .anyMatch(film -> {
                                    System.out.println(">>>>> Test:: returnFilm:" + film);
                                    return film.getTitle().equals("Jurassic World Dominion");
                                }
                        )));
    }

    @Test
    void testCreate() {
        FilmFormDto filmFormDto = FilmFormDto.BBuilder().title("filmRTest1").description("description").
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
                    System.out.println(">>>>> Test:: returnFilm:" + returnFilm);
                    assertEquals("filmRTest1", returnFilm.getTitle());
                    assertEquals("description", returnFilm.getDescription());
                });
    }
}
