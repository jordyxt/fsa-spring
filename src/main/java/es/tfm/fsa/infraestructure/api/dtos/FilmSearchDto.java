package es.tfm.fsa.infraestructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.Rating;
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
public class FilmSearchDto {
    @NotBlank
    private int id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private String releaseYear;
    private List<String> genreList;
    private List<Integer> rateList;

    public FilmSearchDto(Film film) {
        this.id = film.getId();
        this.title = film.getTitle();
        this.description = film.getDescription();
        this.releaseYear = film.getReleaseDate()!=null?Integer.toString(film.getReleaseDate().getYear()):null;
        if (film.getRatingList() != null) {
            this.rateList = film.getRatingList().stream()
                    .map(Rating::getRating).collect(Collectors.toList());
        }
        if (film.getGenreList() != null) {
            this.genreList = film.getGenreList().stream()
                    .map(Genre::getName).collect(Collectors.toList());
        }
    }
}
