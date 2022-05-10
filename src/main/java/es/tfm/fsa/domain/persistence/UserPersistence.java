package es.tfm.fsa.domain.persistence;

import es.tfm.fsa.domain.model.Role;
import es.tfm.fsa.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserPersistence {

    Optional<User> create(User user, Role roleClaim);

    Optional<User> readByUsername(String username);

    Optional<User> update(String username, User user);

    List<User> findBUsernameAndEmailNullSafe(
            String username, String email, Role roleClaim);
}
