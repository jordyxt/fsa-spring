package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.infraestructure.api.RestClientTestService;
import es.tfm.fsa.infraestructure.api.dtos.SeriesSearchDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static es.tfm.fsa.infraestructure.api.resources.SeriesResource.SERIES;
import static es.tfm.fsa.infraestructure.api.resources.SeriesResource.SEARCH;
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
}
