package es.tfm.fsa.infraestructure.postgres.entities;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.VideoProductionType;
import es.tfm.fsa.infraestructure.api.dtos.FilmFormDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString(callSuper=true)
@AllArgsConstructor
@Entity
public class FilmEntity extends VideoProductionEntity {

    public FilmEntity(Film film) {
        super(film);
        this.setVideoProductionType(VideoProductionType.FILM);
    }

    public FilmEntity(FilmFormDto filmFormDto) {
        super(filmFormDto);
        this.setVideoProductionType(VideoProductionType.FILM);
    }

    @Builder(builderMethodName = "BBuilder")
    public FilmEntity(int id, String title, String description,
                      LocalDate releaseDate, List<GenreEntity> genreEntityList,
                      byte[] poster, String trailer, List<VideoProductionWorkerEntity> directorEntityList,
                      List<VideoProductionWorkerEntity> actorEntityList) {
        super(id, title, description, releaseDate, genreEntityList, poster, trailer,
                directorEntityList, actorEntityList, VideoProductionType.FILM);
    }

    public Film toFilm() {
        Film film = new Film();
        BeanUtils.copyProperties(this, film);
        film.setGenreList(this.getGenreEntityList().stream()
                .map(GenreEntity::toGenre)
                .collect(Collectors.toList()));
        film.setDirectorList(this.getDirectorEntityList().stream()
                .map(VideoProductionWorkerEntity::toVideoProductionWorker)
                .collect(Collectors.toList()));
        film.setActorList(this.getActorEntityList().stream()
                .map(VideoProductionWorkerEntity::toVideoProductionWorker)
                .collect(Collectors.toList()));
        return film;
    }
}
