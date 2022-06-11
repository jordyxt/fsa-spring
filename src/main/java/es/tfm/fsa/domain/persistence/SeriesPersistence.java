package es.tfm.fsa.domain.persistence;

import es.tfm.fsa.domain.model.Series;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;
@Repository
public interface SeriesPersistence {
    Optional<Series> create(Series series);

    Optional<Series> findById(int id);

    Optional<Series> update(int id, Series series);

    Stream<Series> findByTitleNullSafe(String title);

    Void delete(String name);
}
