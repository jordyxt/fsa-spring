package es.tfm.fsa.infraestructure.postgres.entities;

import es.tfm.fsa.domain.model.Rating;
import es.tfm.fsa.infraestructure.api.dtos.RatingFormDto;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rating")
public class RatingEntity {
    @EmbeddedId
    private RatingKey id;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name="user_id")
    private UserEntity userEntity;
    @ManyToOne
    @MapsId("videProductionId")
    @JoinColumn(name="video_production_id")
    private VideoProductionEntity videoProductionEntity;
    @NonNull
    private Integer rating;

    @Builder
    public RatingEntity(UserEntity userEntity, VideoProductionEntity videoProductionEntity, Integer rating){
        this.id = new RatingKey(userEntity.getId(),videoProductionEntity.getId());
        this.userEntity = userEntity;
        this.videoProductionEntity = videoProductionEntity;
        this.rating = rating;
    }
    public RatingEntity(RatingFormDto ratingFormDto){
        BeanUtils.copyProperties(rating, this);
    }
    public Rating toRating() {
        Rating rating = new Rating();
        BeanUtils.copyProperties(this, rating);
        return rating;
    }
}
