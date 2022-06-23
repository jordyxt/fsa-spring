package es.tfm.fsa.infraestructure.postgres.daos.synchronous;

import es.tfm.fsa.infraestructure.postgres.entities.VideoProductionEntity;
import es.tfm.fsa.infraestructure.postgres.entities.VideoProductionWorkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface VideoProductionWorkerDao extends JpaRepository<VideoProductionWorkerEntity, Integer > {
}
