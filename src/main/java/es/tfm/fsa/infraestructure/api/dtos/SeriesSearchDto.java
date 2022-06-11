package es.tfm.fsa.infraestructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.Series;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeriesSearchDto {
    @NotBlank
    private int id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String releaseYear;
    @NotBlank
    private String endingYear;
    @NotBlank
    private List<String> genreList;

    public SeriesSearchDto(Series series) {
        this.id = series.getId();
        this.title = series.getTitle();
        this.description = series.getDescription();
        this.releaseYear = series.getReleaseDate()!=null?Integer.toString(series.getReleaseDate().getYear()):null;
        this.endingYear = series.getEndingDate()!=null?Integer.toString(series.getEndingDate().getYear()):null;
        if (series.getGenreList() != null) {
            this.genreList = series.getGenreList().stream()
                    .map(Genre::getName).collect(Collectors.toList());
        }
    }
}
