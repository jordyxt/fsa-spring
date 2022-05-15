package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.configuration.JwtService;
import es.tfm.fsa.domain.model.Role;
import es.tfm.fsa.infraestructure.api.dtos.TokenDto;
import es.tfm.fsa.infraestructure.api.resources.UserResource;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@Service
public class RestClientTestService {

    @Autowired
    private JwtService jwtService;

    private String token;

    private boolean isRole(Role role) {
        return this.token != null && jwtService.role(token).equals(role.name());
    }

    private WebTestClient login(Role role, String username, WebTestClient webTestClient) {
        if (!this.isRole(role)) {
            return login(username, webTestClient);
        } else {
            return webTestClient.mutate()
                    .defaultHeader("Authorization", "Bearer " + this.token).build();
        }
    }

    public WebTestClient login(String username, WebTestClient webTestClient) {
        TokenDto tokenDto = webTestClient
                .mutate().filter(basicAuthentication(username, "12345")).build()
                .post().uri(UserResource.USERS + UserResource.TOKEN)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TokenDto.class)
                .value(Assertions::assertNotNull)
                .returnResult().getResponseBody();
        if (tokenDto != null) {
            this.token = tokenDto.getToken();
        }
        return webTestClient.mutate()
                .defaultHeader("Authorization", "Bearer " + this.token).build();
    }

    public WebTestClient loginAdmin(WebTestClient webTestClient) {
        return this.login(Role.ADMIN, "admin", webTestClient);
    }


    public WebTestClient loginBasic(WebTestClient webTestClient) {
        return this.login(Role.BASIC, "user1", webTestClient);
    }

    public String getToken() {
        return token;
    }

}
