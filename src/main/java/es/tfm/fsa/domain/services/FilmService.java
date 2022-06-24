package es.tfm.fsa.domain.services;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.Rating;
import es.tfm.fsa.domain.model.VideoProductionWorker;
import es.tfm.fsa.domain.persistence.FilmPersistence;
import es.tfm.fsa.domain.persistence.GenrePersistence;
import es.tfm.fsa.domain.persistence.RatingPersistence;
import es.tfm.fsa.infraestructure.api.dtos.FilmFormDto;
import es.tfm.fsa.infraestructure.api.dtos.FilmReviewDto;
import es.tfm.fsa.infraestructure.api.dtos.FilmSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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

    public Optional<Film> findById(int id) {
        return this.filmPersistence.findById(id);
    }

    public Optional<FilmReviewDto> read(int id) {
        return this.filmPersistence.findById(id).map(FilmReviewDto::new).map(filmReviewDto -> {
            filmReviewDto.setRating(BigDecimal.valueOf(this.ratingPersistence.findByVideoProductionId(filmReviewDto.getId()).
                    mapToDouble(Rating::getRating).average().orElse(0.0)).setScale(2, RoundingMode.HALF_UP));
            return filmReviewDto;
        });
    }

    public Stream<FilmSearchDto> findByTitleAndGenreListNullSafe(String title, List<String> genres, List<String> workers) {
        return this.filmPersistence.findByTitleNullSafe(title).filter(film ->
                (genres == null || genres.isEmpty() ||
                    film.getGenreList().stream().map(Genre::getName).
                    collect(Collectors.toList()).containsAll(genres))).
                filter(film -> {
                    if (workers != null && !workers.isEmpty()) {
                        System.out.println(film.getDirectorList());
                        List<VideoProductionWorker> workerListAux = new ArrayList<>(film.getDirectorList());
                        workerListAux.removeAll(film.getActorList());
                        List<VideoProductionWorker> workerList = new ArrayList<>(film.getActorList());
                        workerList.addAll(workerListAux);
                        return (workerList.stream().map(VideoProductionWorker::getName).
                                        collect(Collectors.toList()).containsAll(workers));
                    }else {
                        return true;
                    }
                }).map(FilmSearchDto::new).map(filmSearchDto -> {
            filmSearchDto.setRating(this.ratingPersistence.findByVideoProductionId(filmSearchDto.getId()).
                    mapToDouble(Rating::getRating).average().orElse(0.0));
            return filmSearchDto;
        });
    }
}
