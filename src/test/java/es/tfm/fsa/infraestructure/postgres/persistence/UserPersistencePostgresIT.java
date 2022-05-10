package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.TestConfig;
import es.tfm.fsa.domain.model.Role;
import es.tfm.fsa.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
public class UserPersistencePostgresIT {

    @Autowired
    private UserPersistencePostgres userPersistencePostgres;

    @Test
    void testCreate() {
        this.userPersistencePostgres.create(User.builder().username("tester2").email("e2").role(Role.BASIC).build(), Role.ADMIN);
        Optional<User> user = this.userPersistencePostgres.readByUsername("tester2");
        assertTrue(user.isPresent());
        assertThat(user.get().getUsername(), is("tester2"));
    }
}
