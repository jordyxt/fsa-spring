package es.tfm.fsa.infraestructure.postgres.daos;

import es.tfm.fsa.domain.model.Role;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.UserDao;
import es.tfm.fsa.infraestructure.postgres.entities.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service // @Profile("dev")
public class DatabaseSeederDev {

    private DatabaseStarting databaseStarting;
    private UserDao userDao;

    @Autowired
    public DatabaseSeederDev(UserDao userDao, DatabaseStarting databaseStarting) {
        this.userDao = userDao;
        this.databaseStarting = databaseStarting;
        this.deleteAllAndInitializeAndSeedDataBase();
    }
    public void deleteAllAndInitializeAndSeedDataBase() {
        this.deleteAllAndInitialize();
        this.seedDataBase();
    }

    public void deleteAllAndInitialize() {
        this.userDao.deleteAll();
        LogManager.getLogger(this.getClass()).warn("------- Deleted All -----------");
        this.databaseStarting.initialize();
    }

    private void seedDataBase() {
        LogManager.getLogger(this.getClass()).warn("------- Initial Load from JAVA -----------");
        String pass = new BCryptPasswordEncoder().encode("12345");
        UserEntity[] users = {
                UserEntity.builder().username("adm1").password(pass).email("adm1@gmail.com")
                        .role(Role.ADMIN).registrationDate(LocalDateTime.now()).active(true)
                        .build(),
                UserEntity.builder().username("user1").password(pass).email("user1@gmail.com")
                        .role(Role.BASIC).registrationDate(LocalDateTime.now()).active(true)
                        .build()
        };
        this.userDao.saveAll(List.of(users));
        LogManager.getLogger(this.getClass()).warn("        ------- users");
    }
}
