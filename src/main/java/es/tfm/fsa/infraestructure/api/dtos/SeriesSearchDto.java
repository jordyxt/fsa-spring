package es.tfm.fsa.infraestructure.api.dtos;

import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.Series;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

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
        this.releaseYear = Integer.toString(series.getReleaseDate().getYear());
        this.endingYear = Integer.toString(series.getEndingDate().getYear());
        if (series.getGenreList() != null) {
            this.genreList = series.getGenreList().stream()
                    .map(Genre::getName).collect(Collectors.toList());
        }
    }
}
