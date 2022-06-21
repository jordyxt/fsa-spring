package es.tfm.fsa.infraestructure.api.dtos;

import es.tfm.fsa.domain.model.Series;
import es.tfm.fsa.domain.model.VideoProductionType;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class SeriesReviewDto extends VideoProductionReviewDto {
    @NotBlank
    private LocalDate endingDate;

    public SeriesReviewDto(Series series) {
        super(series);
        this.endingDate = series.getEndingDate();
    }
    @Builder(builderMethodName = "BBuilder")
    public SeriesReviewDto(int id, String title, String description,
                           LocalDate releaseDate, List<String> genreList, String trailer, BigDecimal rating,
                           LocalDate endingYear) {
        super(id, title, description, releaseDate, genreList, trailer, rating, VideoProductionType.FILM);
        this.endingDate = endingYear;
    }
}
