package es.tfm.fsa.domain.persistence;

import es.tfm.fsa.domain.model.VideoProduction;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface VideoProductionPersistence {
    Stream<VideoProduction> findByTitleNullSafe(String title);
}
