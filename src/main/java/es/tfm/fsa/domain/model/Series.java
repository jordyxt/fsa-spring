package es.tfm.fsa.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
@ToString(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Series extends VideoProduction {
    @NotBlank
    private Integer seasons;
    @NotBlank
    private LocalDate endingDate;
    @Builder(builderMethodName = "BBuilder")
    public Series(int id, String title, String description,
                  LocalDate releaseDate, List<Genre> genreList,
                  byte[] poster, String trailer, List<VideoProductionWorker> directorList,
                  List<VideoProductionWorker> actorList, Integer seasons, LocalDate endingDate) {
        super(id, title, description, releaseDate, genreList, poster, trailer,
                directorList, actorList, VideoProductionType.SERIES);
        this.seasons = seasons;
        this.endingDate = endingDate;
    }
}
