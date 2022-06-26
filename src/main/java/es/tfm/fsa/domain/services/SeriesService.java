package es.tfm.fsa.domain.services;

import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.Rating;
import es.tfm.fsa.domain.model.Series;
import es.tfm.fsa.domain.model.VideoProductionWorker;
import es.tfm.fsa.domain.persistence.RatingPersistence;
import es.tfm.fsa.domain.persistence.SeriesPersistence;
import es.tfm.fsa.infraestructure.api.dtos.SeriesFormDto;
import es.tfm.fsa.infraestructure.api.dtos.SeriesReviewDto;
import es.tfm.fsa.infraestructure.api.dtos.SeriesSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SeriesService {
    private final SeriesPersistence seriesPersistence;
    private final RatingPersistence ratingPersistence;

    @Autowired
    public SeriesService(SeriesPersistence seriesPersistence, RatingPersistence ratingPersistence) {
        this.seriesPersistence = seriesPersistence;
        this.ratingPersistence = ratingPersistence;
    }

    public Optional<Series> create(SeriesFormDto seriesFormDto) {
        return this.seriesPersistence.create(seriesFormDto);
    }

    public Optional<Series> findById(int id) {
        return this.seriesPersistence.findById(id);
    }

    public Optional<SeriesReviewDto> read(int id) {
        return this.seriesPersistence.findById(id).map(SeriesReviewDto::new).map(seriesReviewDto -> {
            seriesReviewDto.setRating(BigDecimal.valueOf(this.ratingPersistence.findByVideoProductionId(seriesReviewDto.getId()).
                    mapToDouble(Rating::getRating).average().orElse(0.0)).setScale(2, RoundingMode.HALF_UP));
            return seriesReviewDto;
        });
    }

    public Stream<SeriesSearchDto> findByTitleAndGenreListNullSafe(String title, List<String> genres, List<String> workers) {
        return this.seriesPersistence.findByTitleNullSafe(title).filter(film ->
                (genres == null || genres.isEmpty() ||
                        film.getGenreList().stream().map(Genre::getName).
                                collect(Collectors.toList()).containsAll(genres))).
                filter(series -> {
                    if (workers != null && !workers.isEmpty()) {
                        List<VideoProductionWorker> workerListAux = new ArrayList<>(series.getDirectorList());
                        workerListAux.removeAll(series.getActorList());
                        List<VideoProductionWorker> workerList = new ArrayList<>(series.getActorList());
                        workerList.addAll(workerListAux);
                        return (workerList.stream().map(VideoProductionWorker::getName).
                                collect(Collectors.toList()).containsAll(workers));
                    } else {
                        return true;
                    }
                }).map(SeriesSearchDto::new).map(seriesSearchDto -> {
            seriesSearchDto.setRating(this.ratingPersistence.findByVideoProductionId(seriesSearchDto.getId()).
                    mapToDouble(Rating::getRating).average().orElse(0.0));
            return seriesSearchDto;
        }).sorted(Comparator.comparing(SeriesSearchDto::getRating).reversed());
    }
}
