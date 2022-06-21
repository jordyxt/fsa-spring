package es.tfm.fsa.infraestructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.VideoProduction;
import es.tfm.fsa.domain.model.VideoProductionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoProductionReviewDto {
    @NotBlank
    private int id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private List<String> genreList;
    private String trailer;
    private BigDecimal rating;
    private VideoProductionType videoProductionType;
    public VideoProductionReviewDto(VideoProduction videoProduction) {
        BeanUtils.copyProperties(videoProduction, this);
        if (videoProduction.getGenreList() != null) {
            this.genreList = videoProduction.getGenreList().stream()
                    .map(Genre::getName).collect(Collectors.toList());
        }
    }
}
