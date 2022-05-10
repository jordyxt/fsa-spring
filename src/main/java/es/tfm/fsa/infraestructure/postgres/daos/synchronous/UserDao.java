package es.tfm.fsa.infraestructure.postgres.daos.synchronous;

import es.tfm.fsa.domain.model.Role;
import es.tfm.fsa.infraestructure.postgres.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer >  {

    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findByRoleIn(Collection<Role> roles);
}
