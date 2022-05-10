package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.domain.exceptions.ConflictException;
import es.tfm.fsa.domain.exceptions.ForbiddenException;
import es.tfm.fsa.domain.exceptions.NotFoundException;
import es.tfm.fsa.domain.model.Role;
import es.tfm.fsa.domain.model.User;
import es.tfm.fsa.domain.persistence.UserPersistence;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.UserDao;
import es.tfm.fsa.infraestructure.postgres.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserPersistencePostgres implements UserPersistence {

    private final UserDao userDao;

    @Autowired
    public UserPersistencePostgres(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> create(User user, Role roleClaim) {
        if (!authorizedRoles(roleClaim).contains(user.getRole())) {
            throw new ForbiddenException("Insufficient role to create this user: " + user);
        }
        assertUsernameNotExist(user.getUsername());
        UserEntity userEntity = new UserEntity(user);
        return Optional.of(this.userDao.save(userEntity).toUser());
    }

    @Override
    public Optional<User> readByUsername(String username) {
        return Optional.of(this.userDao.findByUsername(username).orElseThrow(() -> new NotFoundException("The username don't exist: " + username)).toUser());
    }

    @Override
    public Optional<User> update(String username, User user) {
        return Optional.empty();
    }

    @Override
    public List<User> findBUsernameAndEmailNullSafe(String username, String email, Role roleClaim) {
        return null;
    }

    private void assertUsernameNotExist(String username) {
        if (this.userDao.findByUsername(username).isPresent()) {
            throw new ConflictException("The username already exists: " + username);
        }
    }

    private List<Role> authorizedRoles(Role roleClaim) {
        if (Role.ADMIN.equals(roleClaim)) {
            return List.of(Role.ADMIN, Role.BASIC);
        } else if (Role.BASIC.equals(roleClaim)) {
            return List.of(Role.BASIC);
        } else {
            return List.of();
        }
    }
}
