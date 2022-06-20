package es.tfm.fsa.infraestructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.Rating;
import es.tfm.fsa.domain.model.VideoProductionType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString(callSuper=true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilmSearchDto extends VideoProductionSearchDto {
    public FilmSearchDto(Film film) {
        super(film);
    }
    @Builder(builderMethodName = "BBuilder")
    public FilmSearchDto(int id, String title, String description,
                       String releaseDate, List<String> genreList, Double rating) {
        super(id, title, description, releaseDate, genreList, rating, VideoProductionType.FILM);
    }
}
