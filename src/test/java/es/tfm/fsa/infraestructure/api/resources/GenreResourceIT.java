package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.infraestructure.api.RestClientTestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static es.tfm.fsa.infraestructure.api.resources.GenreResource.GENRES;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RestTestConfig
public class GenreResourceIT {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;
    @Test
    void testCreate() {
        Genre genre = Genre.builder().name("genreRTest1").description("description").build();
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(GENRES).body(Mono.just(genre),Genre.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Genre.class)
                .value(Assertions::assertNotNull)
                .value(returnGenre ->{
                    System.out.println(">>>>> Test:: returnGenre:" + returnGenre);
                    assertEquals("genreRTest1", returnGenre.getName());
                    assertEquals("description", returnGenre.getDescription());
                });
    }
}
