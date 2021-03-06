package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.infraestructure.api.RestClientTestService;
import es.tfm.fsa.infraestructure.api.dtos.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static es.tfm.fsa.infraestructure.api.resources.UserResource.BASIC_USERS;
import static es.tfm.fsa.infraestructure.api.resources.UserResource.USERS;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RestTestConfig
public class UserResourceIT {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testCreateUser() {
        this.webTestClient.post().uri(uriBuilder -> uriBuilder
                .path(USERS + BASIC_USERS)
                .build()).
                body(Mono.just(UserDto.builder().username("testerR1").email("e1").password("12345").build()), UserDto.class).
                exchange().expectStatus().isOk();
    }

    @Test
    void testLoginAdmin() {
        this.restClientTestService.loginAdmin(this.webTestClient);
        assertTrue(this.restClientTestService.getToken().length() > 10);
    }

    @Test
    void testLoginBasic() {
        this.restClientTestService.loginBasic(this.webTestClient);
        assertTrue(this.restClientTestService.getToken().length() > 10);
    }
}
