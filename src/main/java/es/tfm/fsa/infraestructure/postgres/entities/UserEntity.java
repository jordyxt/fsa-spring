package es.tfm.fsa.infraestructure.postgres.entities;

import es.tfm.fsa.domain.model.Role;
import es.tfm.fsa.domain.model.User;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "fsaUser") // conflict with user table
public class UserEntity {
    @Id
    @GeneratedValue
    private int id;
    @NonNull
    @Column(unique = true, nullable = false)
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime registrationDate;
    private Boolean active;


    public UserEntity(User user) {
        BeanUtils.copyProperties(user, this);
    }

    public User toUser() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
