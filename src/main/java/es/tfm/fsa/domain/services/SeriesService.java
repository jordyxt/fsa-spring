package es.tfm.fsa.domain.services;

import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.Rating;
import es.tfm.fsa.domain.model.Series;
import es.tfm.fsa.domain.persistence.RatingPersistence;
import es.tfm.fsa.domain.persistence.SeriesPersistence;
import es.tfm.fsa.infraestructure.api.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
public class SeriesService {
    private SeriesPersistence seriesPersistence;
    private RatingPersistence ratingPersistence;
    @Autowired
    public SeriesService(SeriesPersistence seriesPersistence, RatingPersistence ratingPersistence){
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

    public Stream<SeriesSearchDto> findByTitleAndGenreListNullSafe(String title, List<String> genres) {
        return this.seriesPersistence.findByTitleNullSafe(title).filter(series ->
                (genres == null || genres.isEmpty() || series.getGenreList().stream().map(Genre::getName).
                        collect(Collectors.toList()).containsAll(genres))).map(SeriesSearchDto::new).map(seriesSearchDto -> {
            seriesSearchDto.setRating(this.ratingPersistence.findByVideoProductionId(seriesSearchDto.getId()).
                    mapToDouble(Rating::getRating).average().orElse(0.0));
            return seriesSearchDto;
        });
    }
}
