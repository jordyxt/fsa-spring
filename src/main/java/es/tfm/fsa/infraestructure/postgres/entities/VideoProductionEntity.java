package es.tfm.fsa.infraestructure.postgres.entities;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.VideoProduction;
import es.tfm.fsa.domain.model.VideoProductionType;
import es.tfm.fsa.domain.model.VideoProductionWorker;
import es.tfm.fsa.infraestructure.api.dtos.VideoProductionFormDto;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
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
    @ManyToMany
    @JoinTable(
            name = "video_production_genre",
            joinColumns = @JoinColumn(name = "video_production_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<GenreEntity> genreEntityList;
    @Lob
    private byte[] poster;
    private String trailer;
    @ManyToMany
    @JoinTable(
            name = "video_production_director",
            joinColumns = @JoinColumn(name = "video_production_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VideoProductionWorkerEntity> directorEntityList;
    @ManyToMany
    @JoinTable(
            name = "video_production_actor",
            joinColumns = @JoinColumn(name = "video_production_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VideoProductionWorkerEntity> actorEntityList;
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
        this.directorEntityList = new ArrayList<>();
        this.actorEntityList = new ArrayList<>();
    }
    public void add(GenreEntity genreEntity) {
        this.genreEntityList.add(genreEntity);
    }
    public void addDirector(VideoProductionWorkerEntity videoProductionWorkerEntity) {
        this.directorEntityList.add(videoProductionWorkerEntity);
    }
    public void addActor(VideoProductionWorkerEntity videoProductionWorkerEntity) {
        this.actorEntityList.add(videoProductionWorkerEntity);
    }
}
