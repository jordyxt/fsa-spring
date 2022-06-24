package es.tfm.fsa.infraestructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import es.tfm.fsa.domain.model.VideoProductionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoProductionFormDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private List<String> genreList;
    private String poster;
    private String trailer;
    private List<String> directorList;
    private List<String> actorList;
    private VideoProductionType videoProductionType;
    public void doDefault() {
        if (Objects.isNull(genreList)) {
            this.genreList = new ArrayList<String>();
        }
        if (Objects.isNull(directorList)) {
            this.directorList = new ArrayList<String>();
        }
        if (Objects.isNull(actorList)) {
            this.actorList = new ArrayList<String>();
        }
    }
}
