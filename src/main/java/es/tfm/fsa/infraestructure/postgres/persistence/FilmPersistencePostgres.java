package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.domain.exceptions.NotFoundException;
import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.persistence.FilmPersistence;
import es.tfm.fsa.infraestructure.api.dtos.FilmFormDto;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.FilmDao;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.GenreDao;
import es.tfm.fsa.infraestructure.postgres.entities.FilmEntity;
import es.tfm.fsa.infraestructure.postgres.entities.GenreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Repository
public class FilmPersistencePostgres implements FilmPersistence {
    private FilmDao filmDao;
    private GenreDao genreDao;

    @Autowired
    public FilmPersistencePostgres(FilmDao filmDao, GenreDao genreDao) {
        this.filmDao = filmDao;
        this.genreDao = genreDao;
    }

    @Override
    public Optional<Film> create(FilmFormDto filmFormDto) {
        FilmEntity filmEntity = new FilmEntity(filmFormDto);
        filmFormDto.getGenreList().stream().
                map(name -> {
                    if (this.genreDao.findByName(name).isEmpty()) {
                        throw new NotFoundException("Non existent genre name: " + name);
                    }
                    return this.genreDao.findByName(name).get();
                }).
                forEach(filmEntity::add);
        return Optional.of(this.filmDao.save(filmEntity).toFilm());
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
