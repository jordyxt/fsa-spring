package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.infraestructure.api.RestClientTestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static es.tfm.fsa.infraestructure.api.resources.GenreResource.GENRES;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmResourceIT {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;
}
