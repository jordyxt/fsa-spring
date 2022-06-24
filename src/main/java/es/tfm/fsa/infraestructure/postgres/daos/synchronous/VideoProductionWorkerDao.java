package es.tfm.fsa.infraestructure.postgres.daos.synchronous;

import es.tfm.fsa.infraestructure.postgres.entities.GenreEntity;
import es.tfm.fsa.infraestructure.postgres.entities.VideoProductionEntity;
import es.tfm.fsa.infraestructure.postgres.entities.VideoProductionWorkerEntity;
import io.netty.handler.codec.http2.Http2Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface VideoProductionWorkerDao extends JpaRepository<VideoProductionWorkerEntity, Integer > {
    Optional<VideoProductionWorkerEntity> findById(String name);
    @Query("select v from VideoProductionWorkerEntity v where " +
            "(?1 is null or lower(v.name) like lower(concat('%', cast( ?1 as string),'%')))")
    List<VideoProductionWorkerEntity> findByNameContainingNullSafe(String name);

    Optional<VideoProductionWorkerEntity> findByName(String name);
}
