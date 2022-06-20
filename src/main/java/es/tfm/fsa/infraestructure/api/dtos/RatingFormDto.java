package es.tfm.fsa.infraestructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.tfm.fsa.domain.model.User;
import es.tfm.fsa.domain.model.VideoProduction;
import lombok.*;

import javax.validation.constraints.NotBlank;
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RatingFormDto {
    private Integer rating;
    private String username;
    private Integer videoProductionId;
}
