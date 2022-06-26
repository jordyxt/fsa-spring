package es.tfm.fsa.infraestructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.VideoProduction;
import es.tfm.fsa.domain.model.VideoProductionType;
import es.tfm.fsa.domain.model.VideoProductionWorker;
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
public class VideoProductionMyListSearchDto {
    @NotBlank
    private int id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private String releaseYear;
    private List<String> genreList;
    private String trailer;
    private Double rating;
    private List<String> directorList;
    private List<String> actorList;
    private Integer myRating;
    private VideoProductionType videoProductionType;

    public VideoProductionMyListSearchDto(VideoProduction videoProduction) {
        this.id = videoProduction.getId();
        this.title = videoProduction.getTitle();
        this.description = videoProduction.getDescription();
        this.releaseYear = videoProduction.getReleaseDate() != null ? Integer.toString(videoProduction.getReleaseDate().getYear()) : null;
        if (videoProduction.getGenreList() != null) {
            this.genreList = videoProduction.getGenreList().stream()
                    .map(Genre::getName).collect(Collectors.toList());
        }
        if (videoProduction.getDirectorList() != null) {
            this.directorList = videoProduction.getDirectorList().stream()
                    .map(VideoProductionWorker::getName).collect(Collectors.toList());
        }
        if (videoProduction.getActorList() != null) {
            this.actorList = videoProduction.getActorList().stream()
                    .map(VideoProductionWorker::getName).collect(Collectors.toList());
        }
        this.trailer = videoProduction.getTrailer();
        this.videoProductionType = videoProduction.getVideoProductionType();
    }
}
