package es.tfm.fsa.domain.persistence;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Genre;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
@Repository
public interface FilmPersistence {
    Optional<Film> create(Film film);

    Optional<Film> findById(int id);

    Optional<Film> update(int id, Film film);

    Stream<Film> findByTitleNullSafe(String title);

    Void delete(String name);
}
