package es.tfm.fsa.domain.services;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.persistence.FilmPersistence;
import es.tfm.fsa.domain.persistence.GenrePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
public class FilmService {
    private FilmPersistence filmPersistence;
    @Autowired
    public FilmService(FilmPersistence filmPersistence){
        this.filmPersistence = filmPersistence;
    }
    public Optional<Film> create(Film film) {
        return this.filmPersistence.create(film);
    }
    public Optional<Film> read(Integer id) {
        return this.filmPersistence.readById(id);
    }
    public Stream<Film> findByTitleAndGenreListNullSafe(String title, Collection<String> genres) {
        return this.filmPersistence.findByTitleNullSafe(title).filter(film ->
                (film.getGenreList().stream().map(Genre::getDescription).
                        collect(Collectors.toList()).containsAll(genres)));
    }
}
