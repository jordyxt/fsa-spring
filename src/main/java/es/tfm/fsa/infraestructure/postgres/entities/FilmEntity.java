package es.tfm.fsa.infraestructure.postgres.entities;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.VideoProductionType;
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
@AllArgsConstructor
@Entity
public class FilmEntity extends VideoProductionEntity {

    public FilmEntity(Film film) {
        super(film);
    }
    public FilmEntity(FilmFormDto filmFormDto) {
        super(filmFormDto);
    }
    @Builder(builderMethodName = "BBuilder")
    public FilmEntity(int id, String title, String description,
                      LocalDate releaseDate, List<GenreEntity> genreEntityList,
                      byte[] poster, String trailer) {
        super(id,title,description,releaseDate,genreEntityList,poster,trailer, VideoProductionType.FILM);
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
