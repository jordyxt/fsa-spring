package es.tfm.fsa.domain.services;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.Rating;
import es.tfm.fsa.domain.persistence.FilmPersistence;
import es.tfm.fsa.domain.persistence.GenrePersistence;
import es.tfm.fsa.domain.persistence.RatingPersistence;
import es.tfm.fsa.infraestructure.api.dtos.FilmFormDto;
import es.tfm.fsa.infraestructure.api.dtos.FilmSearchDto;
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
    private RatingPersistence ratingPersistence;
    @Autowired
    public FilmService(FilmPersistence filmPersistence, RatingPersistence ratingPersistence){
        this.filmPersistence = filmPersistence;
        this.ratingPersistence = ratingPersistence;
    }
    public Optional<Film> create(FilmFormDto filmFormDto) {
        return this.filmPersistence.create(filmFormDto);
    }
    @Transactional
    public Optional<Film> read(int id) {
        return this.filmPersistence.findById(id);
    }
    @Transactional
    public Stream<FilmSearchDto> findByTitleAndGenreListNullSafe(String title, List<String> genres) {
        return this.filmPersistence.findByTitleNullSafe(title).filter(film ->
                (genres == null || genres.isEmpty() || film.getGenreList().stream().map(Genre::getName).
                        collect(Collectors.toList()).containsAll(genres))).map(FilmSearchDto::new).map(filmSearchDto -> {
            filmSearchDto.setRating(this.ratingPersistence.findByVideoProductionId(filmSearchDto.getId()).
                    mapToDouble(Rating::getRating).average().orElse(Double.NaN));
            return filmSearchDto;
        });
    }
}
