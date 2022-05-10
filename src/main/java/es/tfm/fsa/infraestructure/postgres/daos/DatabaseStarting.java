package es.tfm.fsa.infraestructure.postgres.daos;

import es.tfm.fsa.domain.model.Role;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.UserDao;
import es.tfm.fsa.infraestructure.postgres.entities.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DatabaseStarting {
    private static final String SUPER_USER = "admin";
    private static final String PASSWORD = "12345";

    private UserDao userDao;

    @Autowired
    public DatabaseStarting(UserDao userDao) {
        this.userDao = userDao;
        this.initialize();
    }
    void initialize() {
        LogManager.getLogger(this.getClass()).warn("------- Finding Admin -----------");
        if (this.userDao.findByRoleIn(List.of(Role.ADMIN)).isEmpty()) {
            UserEntity user = UserEntity.builder().username(SUPER_USER)
                    .password(new BCryptPasswordEncoder().encode(PASSWORD))
                    .role(Role.ADMIN).registrationDate(LocalDateTime.now()).active(true).build();
            this.userDao.save(user);
            LogManager.getLogger(this.getClass()).warn("------- Created Admin -----------");
        }
    }
}
