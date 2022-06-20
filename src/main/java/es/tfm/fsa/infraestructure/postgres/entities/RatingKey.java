package es.tfm.fsa.infraestructure.postgres.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RatingKey implements Serializable {

    @Column(name = "user_id")
    Integer userId;
    @Column(name = "video_production_id")
    Integer videProductionId;
}
