package es.tfm.fsa.domain.services;

import es.tfm.fsa.TestConfig;
import es.tfm.fsa.domain.model.Role;
import es.tfm.fsa.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Test
    void testCreateUser() {
        this.userService.createUser(User.builder().username("tester1").email("e1").role(Role.BASIC).build(), Role.ADMIN);
        Optional<User> user = this.userService.readByUsername("tester1");
        assertTrue(user.isPresent());
        assertThat(user.get().getUsername(), is("tester1"));
    }
}
