package es.tfm.fsa.infraestructure.postgres.entities;

import es.tfm.fsa.domain.model.Series;
import es.tfm.fsa.domain.model.VideoProductionType;
import es.tfm.fsa.infraestructure.api.dtos.SeriesFormDto;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SeriesEntity extends VideoProductionEntity {
    private Integer seasons;
    private LocalDate endingDate;

    public SeriesEntity(Series series) {
        super(series);
        this.seasons = series.getSeasons();
        this.endingDate = series.getEndingDate();
    }

    public SeriesEntity(SeriesFormDto seriesFormDto) {
        super(seriesFormDto);
        this.seasons = seriesFormDto.getSeasons();
        this.endingDate = seriesFormDto.getEndingDate();
    }

    @Builder(builderMethodName = "BBuilder")
    public SeriesEntity(int id, String title, String description,
                        LocalDate releaseDate, List<GenreEntity> genreEntityList,
                        byte[] poster, String trailer, List<VideoProductionWorkerEntity> directorEntityList,
                        List<VideoProductionWorkerEntity> actorEntityList, Integer seasons, LocalDate endingDate) {
        super(id, title, description, releaseDate, genreEntityList, poster, trailer,
                directorEntityList, actorEntityList, VideoProductionType.SERIES);
        this.seasons = seasons;
        this.endingDate = endingDate;
    }

    public Series toSeries() {
        Series series = new Series();
        BeanUtils.copyProperties(this, series);
        series.setGenreList(this.getGenreEntityList().stream()
                .map(GenreEntity::toGenre)
                .collect(Collectors.toList()));
        series.setDirectorList(this.getDirectorEntityList().stream()
                .map(VideoProductionWorkerEntity::toVideoProductionWorker)
                .collect(Collectors.toList()));
        series.setActorList(this.getActorEntityList().stream()
                .map(VideoProductionWorkerEntity::toVideoProductionWorker)
                .collect(Collectors.toList()));
        return series;
    }
}
