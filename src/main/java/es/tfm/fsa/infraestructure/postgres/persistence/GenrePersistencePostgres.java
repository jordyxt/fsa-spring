package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.domain.exceptions.ConflictException;
import es.tfm.fsa.domain.exceptions.NotFoundException;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.persistence.GenrePersistence;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.GenreDao;
import es.tfm.fsa.infraestructure.postgres.entities.GenreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class GenrePersistencePostgres implements GenrePersistence {
    private final GenreDao genreDao;

    @Autowired
    public GenrePersistencePostgres(GenreDao genreDao) {
        this.genreDao = genreDao;
    }
    @Override
    public Optional<Genre> create(Genre genre) {
        assertNameNotExist(genre.getName());
        GenreEntity genreEntity = new GenreEntity(genre);
        return Optional.of(this.genreDao.save(genreEntity).toGenre());
    }

    @Override
    public Optional<Genre> readByName(String name) {
        if (this.genreDao.findByName(name).isEmpty()) {
            throw new NotFoundException("Non existent genre name: " + name);
        }
        return  Optional.of(this.genreDao.findByName(name).get().toGenre());
    }

    @Override
    public Stream<Genre> findByNameAndDescriptionContainingNullSafe(String name, String description) {
        return this.genreDao.findByNameAndDescriptionContainingNullSafe(name, description).stream().map(GenreEntity::toGenre);
    }

    private void assertNameNotExist(String name) {
        if (this.genreDao.findByName(name).isPresent()) {
            throw new ConflictException("The genre already exists: " + name);
        }
    }
}
