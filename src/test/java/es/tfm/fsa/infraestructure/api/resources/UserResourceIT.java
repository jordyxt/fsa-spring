package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.infraestructure.api.dtos.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static es.tfm.fsa.infraestructure.api.resources.UserResource.*;

@RestTestConfig
public class UserResourceIT {
    @Autowired
    private WebTestClient webTestClient;
    @Test
    void testCreateUser() {
        this.webTestClient.post().uri(uriBuilder -> uriBuilder
                .path(USERS + BASIC_USERS)
                .build()).
                body(Mono.just(UserDto.builder().username("tester2").email("e1").password("12345").build()), UserDto.class).
                exchange().expectStatus().isOk();
    }
}
