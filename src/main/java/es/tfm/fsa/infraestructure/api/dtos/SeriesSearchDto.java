package es.tfm.fsa.infraestructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.tfm.fsa.domain.model.Series;
import es.tfm.fsa.domain.model.VideoProductionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeriesSearchDto extends VideoProductionSearchDto {
    @NotBlank
    private String endingYear;

    public SeriesSearchDto(Series series) {
        super(series);
        this.endingYear = series.getEndingDate() != null ? Integer.toString(series.getEndingDate().getYear()) : null;
    }

    @Builder(builderMethodName = "BBuilder")
    public SeriesSearchDto(int id, String title, String description,
                           String releaseDate, List<String> genreList, String trailer, Double rating,
                           List<String> directorList, List<String> actorList, String endingYear) {
        super(id, title, description, releaseDate, genreList, trailer, rating,
                directorList, actorList, VideoProductionType.SERIES);
        this.endingYear = endingYear;
    }
}
