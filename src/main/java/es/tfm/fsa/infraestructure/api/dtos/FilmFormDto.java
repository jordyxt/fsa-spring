package es.tfm.fsa.infraestructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.tfm.fsa.domain.model.VideoProductionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilmFormDto extends VideoProductionFormDto {
    @Builder(builderMethodName = "BBuilder")
    public FilmFormDto(String title, String description,
                       LocalDate releaseDate, List<String> genreList,
                       String poster, String trailer, List<String> directorList, List<String> actorList) {
        super(title, description, releaseDate, genreList, poster, trailer,
                directorList, actorList, VideoProductionType.FILM);
    }
}
