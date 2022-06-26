package es.tfm.fsa.domain.persistence;

import es.tfm.fsa.domain.model.VideoProductionWorker;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface VideoProductionWorkerPersistence {
    Optional<VideoProductionWorker> create(VideoProductionWorker videoProductionWorker);

    Optional<VideoProductionWorker> findById(Integer id);

    Optional<VideoProductionWorker> update(Integer id, VideoProductionWorker videoProductionWorker);

    Stream<VideoProductionWorker> findByNameContainingNullSafe(String name);

    Void delete(Integer id);
}
