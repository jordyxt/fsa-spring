package es.tfm.fsa.infraestructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.tfm.fsa.domain.model.Series;
import es.tfm.fsa.domain.model.VideoProductionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Data
@ToString(callSuper=true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeriesReviewDto extends VideoProductionReviewDto {
    private Integer seasons;
    @NotBlank
    private LocalDate endingDate;

    public SeriesReviewDto(Series series) {
        super(series);
        this.endingDate = series.getEndingDate();
    }
    @Builder(builderMethodName = "BBuilder")
    public SeriesReviewDto(int id, String title, String description,
                           LocalDate releaseDate, List<String> genreList, String trailer, BigDecimal rating,
                           Integer seasons, LocalDate endingYear) {
        super(id, title, description, releaseDate, genreList, trailer, rating, VideoProductionType.SERIES);
        this.seasons = seasons;
        this.endingDate = endingYear;
    }
}
