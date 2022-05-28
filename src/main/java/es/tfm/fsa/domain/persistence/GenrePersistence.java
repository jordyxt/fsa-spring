package es.tfm.fsa.domain.persistence;

import es.tfm.fsa.domain.model.Genre;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenrePersistence {
    Optional<Genre> create(Genre genre);
}
