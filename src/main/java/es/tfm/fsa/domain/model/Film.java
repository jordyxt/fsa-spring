package es.tfm.fsa.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@ToString(callSuper=true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Film extends VideoProduction {
    @Builder(builderMethodName = "BBuilder")
    public Film(int id, String title, String description,
                LocalDate releaseDate, List<Genre> genreList,
                byte[] poster, String trailer, List<Rating> ratingList) {
        super(id, title, description, releaseDate, genreList, poster, trailer, ratingList, VideoProductionType.FILM);
    }
}
