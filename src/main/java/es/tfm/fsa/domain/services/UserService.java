package es.tfm.fsa.domain.services;

import es.tfm.fsa.configuration.JwtService;
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
    private JwtService jwtService;

    @Autowired
    public UserService(UserPersistence userPersistence, JwtService jwtService) {
        this.userPersistence = userPersistence;
        this.jwtService = jwtService;
    }

    public Optional<String> login(String username) {
        return this.userPersistence.readByUsername(username)
                .map(user -> jwtService.createToken(user.getUsername(), user.getRole().name()));
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
