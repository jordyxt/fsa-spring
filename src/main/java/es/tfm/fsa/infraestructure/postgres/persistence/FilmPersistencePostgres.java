package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.persistence.FilmPersistence;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.FilmDao;
import es.tfm.fsa.infraestructure.postgres.entities.FilmEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public Optional<Film> findById(int id) {
        return this.filmDao.findById(id).map(FilmEntity::toFilm);
    }

    @Override
    public Optional<Film> update(int id, Film film) {
        return Optional.empty();
    }

    @Override
    public Stream<Film> findByTitleNullSafe(String title) {
        return this.filmDao.findByTitleContaining(title).stream().map(FilmEntity::toFilm);
    }

    @Override
    public Void delete(String name) {
        return null;
    }
}
