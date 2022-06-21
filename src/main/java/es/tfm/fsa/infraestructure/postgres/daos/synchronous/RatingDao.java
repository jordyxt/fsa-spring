package es.tfm.fsa.infraestructure.postgres.daos.synchronous;

import es.tfm.fsa.infraestructure.postgres.entities.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
@Transactional
public interface RatingDao extends JpaRepository<RatingEntity, Integer > {

    Optional<RatingEntity> findByUserEntityUsernameAndVideoProductionEntityId(String username, int videoProductionId);
    List<RatingEntity> findByUserEntityUsername(String username);
    List<RatingEntity> findByVideoProductionEntityId(int videoProductionEntityId);
}
