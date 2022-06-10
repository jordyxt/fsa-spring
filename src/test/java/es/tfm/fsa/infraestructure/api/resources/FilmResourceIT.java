package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.infraestructure.api.RestClientTestService;
import es.tfm.fsa.infraestructure.api.dtos.FilmSearchDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static es.tfm.fsa.infraestructure.api.resources.FilmResource.FILMS;
import static es.tfm.fsa.infraestructure.api.resources.FilmResource.SEARCH;
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
                        .queryParam("genreList",  "action,adventure,sci-fi")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FilmSearchDto.class)
                .value(Assertions::assertNotNull)
                .value(films -> {
                    assertTrue(films.stream()
                            .anyMatch(film ->
                                    film.getTitle().equals("Jurassic World Dominion")
                            ));
                });
    }
}
