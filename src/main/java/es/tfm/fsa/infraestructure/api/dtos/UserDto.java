package es.tfm.fsa.infraestructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.tfm.fsa.domain.model.Role;
import es.tfm.fsa.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    @NotNull
    @NotBlank
    private String username;
    private String email;
    private String password;
    private Role role;
    private Boolean active;
    private LocalDateTime registrationDate;

    public UserDto(User user){
        BeanUtils.copyProperties(user, this);
        this.password = "secret";
    }
    public void doDefault() {
        if (Objects.isNull(password)) {
            password = UUID.randomUUID().toString();
        }
        if (Objects.isNull(role)) {
            this.role = Role.BASIC;
        }
        if (Objects.isNull(active)) {
            this.active = true;
        }
    }
    public User toUser() {
        this.doDefault();
        this.password = new BCryptPasswordEncoder().encode(this.password);
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
