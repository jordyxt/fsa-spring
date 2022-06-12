package es.tfm.fsa.infraestructure.postgres.entities;

import es.tfm.fsa.domain.model.Series;
import es.tfm.fsa.infraestructure.api.dtos.FilmFormDto;
import es.tfm.fsa.infraestructure.api.dtos.SeriesFormDto;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "series")
public class SeriesEntity {
    @Id
    @GeneratedValue
    private int id;
    @NonNull
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer seasons;
    private LocalDate endingDate;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "series_genre",
            joinColumns = @JoinColumn(name = "series_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<GenreEntity> genreEntityList;
    @Lob
    private byte[] poster;

    public SeriesEntity(Series series) {
        BeanUtils.copyProperties(series, this);
        this.genreEntityList = new ArrayList<>();
    }
    public SeriesEntity(SeriesFormDto seriesFormDto) {
        BeanUtils.copyProperties(seriesFormDto, this);
        this.poster = seriesFormDto.getPoster()!=null?
                Base64.getDecoder().decode(seriesFormDto.getPoster().split(",")[1]):null;
        this.genreEntityList = new ArrayList<>();
    }
    public void add(GenreEntity genreEntity) {
        this.genreEntityList.add(genreEntity);
    }

    public Series toSeries() {
        Series series = new Series();
        BeanUtils.copyProperties(this, series);
        series.setGenreList(this.getGenreEntityList().stream()
                .map(GenreEntity::toGenre)
                .collect(Collectors.toList()));
        return series;
    }
}
