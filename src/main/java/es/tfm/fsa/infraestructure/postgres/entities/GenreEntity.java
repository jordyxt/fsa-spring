package es.tfm.fsa.infraestructure.postgres.entities;

import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.User;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "genre")
public class GenreEntity {
    @Id
    @GeneratedValue
    private int id;
    @NonNull
    @Column(unique = true, nullable = false)
    private String name;
    private String description;

    public GenreEntity(Genre genre) {
        BeanUtils.copyProperties(genre, this);
    }

    public Genre toGenre() {
        Genre genre = new Genre();
        BeanUtils.copyProperties(this, genre);
        return genre;
    }
}
