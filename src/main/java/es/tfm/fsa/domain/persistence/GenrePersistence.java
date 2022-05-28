package es.tfm.fsa.domain.persistence;

import es.tfm.fsa.domain.model.Genre;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface GenrePersistence {
    Optional<Genre> create(Genre genre);

    Optional<Genre> readByName(String name);

    Stream<Genre> findByNameAndDescriptionNullSafe(String name, String description);
}
