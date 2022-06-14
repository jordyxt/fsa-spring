package es.tfm.fsa.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Film extends VideoProduction {
    @Builder(builderMethodName = "BBuilder")
    public Film(int id, String title, String description,
                LocalDate releaseDate, List<Genre> genreList,
                byte[] poster,String trailer) {
        super(id, title, description, releaseDate, genreList, poster, trailer, VideoProductionType.FILM);
    }
}
