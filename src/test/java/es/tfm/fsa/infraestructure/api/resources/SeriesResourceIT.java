package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Series;
import es.tfm.fsa.infraestructure.api.RestClientTestService;
import es.tfm.fsa.infraestructure.api.dtos.FilmFormDto;
import es.tfm.fsa.infraestructure.api.dtos.SeriesFormDto;
import es.tfm.fsa.infraestructure.api.dtos.SeriesSearchDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static es.tfm.fsa.infraestructure.api.resources.FilmResource.FILMS;
import static es.tfm.fsa.infraestructure.api.resources.SeriesResource.SERIES;
import static es.tfm.fsa.infraestructure.api.resources.SeriesResource.SEARCH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@RestTestConfig
public class SeriesResourceIT {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;
    @Test
    void testSearch() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(SERIES + SEARCH)
                        .queryParam("genreList",  "crime")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(SeriesSearchDto.class)
                .value(Assertions::assertNotNull)
                .value(seriesList -> {
                    assertTrue(seriesList.stream()
                            .anyMatch(series ->
                                    series.getTitle().equals("Money Heist")
                            ));
                });
    }
    @Test
    void testCreate() {
        SeriesFormDto seriesFormDto = SeriesFormDto.builder().title("seriesRTest1").description("description").
                releaseDate(LocalDate.of(2022, Month.JANUARY,1)).
                genreList(Arrays.asList("action","adventure","sci-fi")).build();
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(SERIES).body(Mono.just(seriesFormDto),SeriesFormDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Series.class)
                .value(Assertions::assertNotNull)
                .value(returnFilm ->{
                    System.out.println(">>>>> Test:: returnSeries:" + returnFilm);
                    assertEquals("seriesRTest1", returnFilm.getTitle());
                    assertEquals("description", returnFilm.getDescription());
                });
    }
}
