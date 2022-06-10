package es.tfm.fsa.domain.services;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.persistence.FilmPersistence;
import es.tfm.fsa.domain.persistence.GenrePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
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
    @Transactional
    public Optional<Film> read(int id) {
        return this.filmPersistence.findById(id);
    }
    @Transactional
    public Stream<Film> findByTitleAndGenreListNullSafe(String title, List<String> genres) {
        return this.filmPersistence.findByTitleNullSafe(title).filter(film ->
                (genres == null || genres.isEmpty() || film.getGenreList().stream().map(Genre::getName).
                        collect(Collectors.toList()).containsAll(genres)));
    }
}
