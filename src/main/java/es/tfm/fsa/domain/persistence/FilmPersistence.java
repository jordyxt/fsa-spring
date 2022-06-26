package es.tfm.fsa.domain.persistence;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.infraestructure.api.dtos.FilmFormDto;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface FilmPersistence {
    Optional<Film> create(FilmFormDto filmFormDto);

    Optional<Film> findById(int id);

    Optional<Film> update(int id, Film film);

    Stream<Film> findByTitleNullSafe(String title);

    Void delete(String name);
}
