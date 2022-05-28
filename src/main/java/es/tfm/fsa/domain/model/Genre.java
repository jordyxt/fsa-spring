package es.tfm.fsa.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Genre {
    @NotBlank
    private String name;
    @NotBlank
    private String description;

    public static Genre ofNameDescription(Genre genre) {
        return Genre.builder()
                .name(genre.getName())
                .description(genre.getDescription())
                .build();
    }
}
