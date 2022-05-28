package es.tfm.fsa.domain.services;

import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.persistence.GenrePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
