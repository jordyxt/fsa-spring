package es.tfm.fsa.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Film extends VideoProduction {
    @Builder(builderMethodName = "BBuilder")
    public Film(int id, String title, String description,
                LocalDate releaseDate, List<Genre> genreList,
                byte[] poster, String trailer, List<VideoProductionWorker> directorList,
                List<VideoProductionWorker> actorList) {
        super(id, title, description, releaseDate, genreList, poster, trailer,
                directorList, actorList, VideoProductionType.FILM);
    }
}
