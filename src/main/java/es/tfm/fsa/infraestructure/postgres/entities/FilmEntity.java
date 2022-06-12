package es.tfm.fsa.infraestructure.postgres.entities;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.infraestructure.api.dtos.FilmFormDto;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "film")
public class FilmEntity {
    @Id
    @GeneratedValue
    private int id;
    @NonNull
    private String title;
    private String description;
    private LocalDate releaseDate;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "film_genre",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<GenreEntity> genreEntityList;
    @Lob
    private byte[] poster;
    private String trailer;

    public FilmEntity(Film film) {
        BeanUtils.copyProperties(film, this);
        this.genreEntityList = new ArrayList<>();
    }
    public FilmEntity(FilmFormDto filmFormDto) {
        BeanUtils.copyProperties(filmFormDto, this);
        this.poster = filmFormDto.getPoster()!=null?
                Base64.getDecoder().decode(filmFormDto.getPoster().split(",")[1]):null;
        this.genreEntityList = new ArrayList<>();
    }

    public void add(GenreEntity genreEntity) {
        this.genreEntityList.add(genreEntity);
    }

    public Film toFilm() {
        Film film = new Film();
        BeanUtils.copyProperties(this, film);
        film.setGenreList(this.getGenreEntityList().stream()
                .map(GenreEntity::toGenre)
                .collect(Collectors.toList()));
        return film;
    }
}
