package es.tfm.fsa.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
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
                byte[] poster, String trailer, Integer seasons, LocalDate endingDate) {
        super(id, title, description, releaseDate, genreList, poster, trailer, VideoProductionType.SERIES);
        this.seasons = seasons;
        this.endingDate = endingDate;
    }
}
