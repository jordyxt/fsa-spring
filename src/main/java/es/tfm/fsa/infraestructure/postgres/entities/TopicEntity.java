package es.tfm.fsa.infraestructure.postgres.entities;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Topic;
import es.tfm.fsa.infraestructure.api.dtos.TopicFormDto;
import es.tfm.fsa.infraestructure.api.dtos.VideoProductionFormDto;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "topic")
public class TopicEntity {
    @Id
    @GeneratedValue
    private int id;
    @NonNull
    private String title;
    private String description;
    private LocalDateTime creationDate;
    @ManyToOne
    @JoinColumn(name="video_production_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VideoProductionEntity videoProductionEntity;
    @ManyToOne
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity userEntity;
    public TopicEntity(TopicFormDto topicFormDto) {
        BeanUtils.copyProperties(topicFormDto, this);
        this.creationDate = LocalDateTime.now();
    }

    public Topic toTopic() {
        Topic topic = new Topic();
        BeanUtils.copyProperties(this, topic);
        topic.setUser(this.userEntity.toUser());
        topic.setVideoProduction(this.videoProductionEntity.toVideoProduction());
        return topic;
    }
}
