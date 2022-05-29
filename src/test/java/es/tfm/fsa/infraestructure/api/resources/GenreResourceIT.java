package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.infraestructure.api.RestClientTestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

import static es.tfm.fsa.infraestructure.api.resources.GenreResource.GENRES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @Test
    void testUpdate() {
        Genre genre = Genre.builder().name("tagTest2").description("description").build();
        this.restClientTestService.loginAdmin(webTestClient)
                .put()
                .uri(GENRES + "/name2")
                .body(Mono.just(genre), Genre.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Genre.class)
                .value(Assertions::assertNotNull)
                .value(returnTag -> {
                    System.out.println(">>>>> Test:: returnTag:" + returnTag);
                    assertEquals("tagTest2", returnTag.getName());
                    assertEquals("description", returnTag.getDescription());
                });
    }
    @Test
    void testRead() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(GENRES + "/name1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Genre.class)
                .value(Assertions::assertNotNull)
                .value(returnTag -> {
                    assertEquals("name1", returnTag.getName());
                    assertEquals("description", returnTag.getDescription());
                });
    }
    @Test
    void testDelete() {
        Genre genre = Genre.builder().name("genreRTest2").description("description").build();
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(GENRES).body(Mono.just(genre),Genre.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Genre.class)
                .value(Assertions::assertNotNull)
                .value(returnGenre ->{
                    System.out.println(">>>>> Test:: returnGenre:" + returnGenre);
                    assertEquals("genreRTest2", returnGenre.getName());
                    assertEquals("description", returnGenre.getDescription());
                });
        this.restClientTestService.loginAdmin(webTestClient)
                .delete()
                .uri(GENRES + "/genreRTest2")
                .exchange()
                .expectStatus().isNoContent();
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(GENRES + "/genreRTest2")
                .exchange()
                .expectStatus().isNotFound();
    }
}
