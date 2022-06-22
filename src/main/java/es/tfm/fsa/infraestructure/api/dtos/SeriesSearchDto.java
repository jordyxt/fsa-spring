package es.tfm.fsa.infraestructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.Series;
import es.tfm.fsa.domain.model.VideoProductionType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;
@Data
@ToString(callSuper=true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeriesSearchDto extends VideoProductionSearchDto {
    @NotBlank
    private String endingYear;

    public SeriesSearchDto(Series series) {
        super(series);
        this.endingYear = series.getEndingDate()!=null?Integer.toString(series.getEndingDate().getYear()):null;
    }
    @Builder(builderMethodName = "BBuilder")
    public SeriesSearchDto(int id, String title, String description,
                         String releaseDate, List<String> genreList, String trailer, Double rating, String endingYear) {
        super(id, title, description, releaseDate, genreList, trailer, rating, VideoProductionType.SERIES);
        this.endingYear = endingYear;
    }
}
