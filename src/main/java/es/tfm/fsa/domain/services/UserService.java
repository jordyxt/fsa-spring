package es.tfm.fsa.domain.services;

import es.tfm.fsa.domain.persistence.UserPersistence;
import es.tfm.fsa.domain.model.User;
import es.tfm.fsa.domain.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserPersistence userPersistence;

    @Autowired
    public UserService(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    public Optional<User> createUser(User user, Role roleClaim) {
        return this.userPersistence.create(user, roleClaim);
    }

    public List<User> findBUsernameAndEmailNullSafe(
            String username, String email, Role roleClaim) {
        return this.userPersistence.findBUsernameAndEmailNullSafe(
                username, email, roleClaim);
    }

    public Optional<User> readByUsername(String username) {
        return this.userPersistence.readByUsername(username);
    }
}
