package es.tfm.fsa.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    @Test
    void testEmailUser() {
        User x = User.builder().username("Test").email("e")
                .password("1").build();
        assertEquals("Test", x.getUsername());
        assertEquals("e", x.getEmail());
    }
}
