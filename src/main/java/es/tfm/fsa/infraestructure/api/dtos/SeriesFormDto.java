package es.tfm.fsa.infraestructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import es.tfm.fsa.domain.model.VideoProductionType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeriesFormDto extends VideoProductionFormDto {
    private Integer seasons;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endingDate;

    @Builder(builderMethodName = "BBuilder")
    public SeriesFormDto(String title, String description,
                         LocalDate releaseDate, List<String> genreList,
                         String poster, String trailer, List<String> directorList,
                         List<String> actorList, Integer seasons, LocalDate endingDate) {
        super(title, description, releaseDate, genreList, poster, trailer,
                directorList, actorList, VideoProductionType.SERIES);
        this.seasons = seasons;
        this.endingDate = endingDate;
    }
}
