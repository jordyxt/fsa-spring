package es.tfm.fsa.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Film {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private LocalDate releaseDate;
    @NotBlank
    private List<Genre> genreList;
    @NotBlank
    private Byte[] poster;
}
