package es.tfm.fsa.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    @NotBlank
    private int id;
    @NotBlank
    private String message;
    @NotBlank
    private LocalDateTime creationDate;
    @NotBlank
    private Topic topic;
    @NotBlank
    private User user;
}
