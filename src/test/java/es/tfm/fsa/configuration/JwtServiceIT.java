package es.tfm.fsa.configuration;

import es.tfm.fsa.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
class JwtServiceIT {

    @Autowired
    private JwtService jwtService;

    @Test
    void testJwtExceptionNotBearer() {
        assertTrue(jwtService.user("Not Bearer").isEmpty());
    }

    @Test
    void testJwtUtilExtract() {
        assertEquals("t.t.t", jwtService.extractToken("Bearer t.t.t"));
    }

    @Test
    void testCreateTokenAndVerify() {
        String token = jwtService.createToken("user-id", "ROLE");
        assertEquals(3, token.split("\\.").length);
        assertTrue(token.length() > 30);
        assertEquals("user-id", jwtService.user(token));
        assertEquals("ROLE", jwtService.role(token));
    }

}
