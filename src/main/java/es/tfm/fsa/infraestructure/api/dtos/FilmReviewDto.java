package es.tfm.fsa.infraestructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.VideoProductionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilmReviewDto extends VideoProductionReviewDto {
    public FilmReviewDto(Film film) {
        super(film);
    }

    @Builder(builderMethodName = "BBuilder")
    public FilmReviewDto(int id, String title, String description,
                         LocalDate releaseDate, List<String> genreList, String trailer, BigDecimal rating,
                         List<String> directorList, List<String> actorList) {
        super(id, title, description, releaseDate, genreList, trailer, rating,
                directorList, actorList, VideoProductionType.FILM);
    }
}
