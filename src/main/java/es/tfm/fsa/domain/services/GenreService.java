package es.tfm.fsa.domain.services;

import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.persistence.GenrePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class GenreService {

    private GenrePersistence genrePersistence;

    @Autowired
    public GenreService(GenrePersistence genrePersistence){
        this.genrePersistence = genrePersistence;
    }
    public Optional<Genre> create(Genre genre) {
        return this.genrePersistence.create(genre);
    }

    public Optional<Genre> read(String name) {
        return this.genrePersistence.readByName(name);
    }

    public Stream<Genre> findByNameAndDescriptionContainingNullSafe(String name, String description) {
        return this.genrePersistence.findByNameAndDescriptionContainingNullSafe(name, description);
    }
}
