package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.persistence.FilmPersistence;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.FilmDao;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.GenreDao;
import es.tfm.fsa.infraestructure.postgres.entities.FilmEntity;
import es.tfm.fsa.infraestructure.postgres.entities.GenreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;
@Repository
public class FilmPersistencePostgres implements FilmPersistence {
    private FilmDao filmDao;

    @Autowired
    public FilmPersistencePostgres(FilmDao filmDao) {
        this.filmDao = filmDao;
    }
    @Override
    public Optional<Film> create(Film film) {
        return Optional.empty();
    }

    @Override
    public Optional<Film> readById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<Film> update(Integer id, Film film) {
        return Optional.empty();
    }

    @Override
    public Stream<Film> findByTitleNullSafe(String title) {
        return this.filmDao.findByTitle(title).stream().map(FilmEntity::toFilm);
    }

    @Override
    public Void delete(String name) {
        return null;
    }
}
