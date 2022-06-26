package es.tfm.fsa.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime registrationDate;
    private Boolean active;
}
