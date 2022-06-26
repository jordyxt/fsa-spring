package es.tfm.fsa.infraestructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.VideoProductionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilmSearchDto extends VideoProductionSearchDto {
    public FilmSearchDto(Film film) {
        super(film);
    }

    @Builder(builderMethodName = "BBuilder")
    public FilmSearchDto(int id, String title, String description,
                         String releaseDate, List<String> genreList, String trailer,
                         List<String> directorList, List<String> actorList, Double rating) {
        super(id, title, description, releaseDate, genreList, trailer, rating,
                directorList, actorList, VideoProductionType.FILM);
    }
}
