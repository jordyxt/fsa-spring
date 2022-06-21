package es.tfm.fsa.infraestructure.postgres.daos.synchronous;

import es.tfm.fsa.infraestructure.postgres.entities.FilmEntity;
import es.tfm.fsa.infraestructure.postgres.entities.VideoProductionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
@Transactional
public interface VideoProductionDao extends JpaRepository<VideoProductionEntity, Integer > {
    Optional<VideoProductionEntity> findById(int id);

    Optional<VideoProductionEntity> findByTitle(String title);

    @Query("select v from VideoProductionEntity v where " +
            "(?1 is null or lower(v.title) like lower(concat('%', cast( ?1 as string),'%')))")
    List<VideoProductionEntity> findByTitleContaining(String title);
}
