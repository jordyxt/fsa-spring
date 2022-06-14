package es.tfm.fsa.infraestructure.postgres.entities;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.VideoProduction;
import es.tfm.fsa.domain.model.VideoProductionType;
import es.tfm.fsa.infraestructure.api.dtos.FilmFormDto;
import es.tfm.fsa.infraestructure.api.dtos.VideoProductionFormDto;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "video_production")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class VideoProductionEntity {
    @Id
    @GeneratedValue
    private int id;
    @NonNull
    private String title;
    private String description;
    private LocalDate releaseDate;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "video_production_genre",
            joinColumns = @JoinColumn(name = "video_production_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<GenreEntity> genreEntityList;
    @Lob
    private byte[] poster;
    private String trailer;
    private VideoProductionType videoProductionType;
    public VideoProductionEntity(VideoProduction videoProduction) {
        BeanUtils.copyProperties(videoProduction, this);
        this.genreEntityList = new ArrayList<>();
    }
    public VideoProductionEntity(VideoProductionFormDto videoProductionFormDto) {
        BeanUtils.copyProperties(videoProductionFormDto, this);
        this.poster = videoProductionFormDto.getPoster()!=null?
                Base64.getDecoder().decode(videoProductionFormDto.getPoster().split(",")[1]):null;
        this.genreEntityList = new ArrayList<>();
    }
    public void add(GenreEntity genreEntity) {
        this.genreEntityList.add(genreEntity);
    }
}
