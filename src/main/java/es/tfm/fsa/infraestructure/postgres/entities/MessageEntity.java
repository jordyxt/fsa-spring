package es.tfm.fsa.infraestructure.postgres.entities;

import es.tfm.fsa.domain.model.Message;
import es.tfm.fsa.domain.model.Topic;
import es.tfm.fsa.infraestructure.api.dtos.MessageFormDto;
import es.tfm.fsa.infraestructure.api.dtos.TopicFormDto;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "message")
public class MessageEntity {
    @Id
    @GeneratedValue
    private int id;
    @NonNull
    private String message;
    private LocalDateTime creationDate;
    @ManyToOne
    @JoinColumn(name="topic_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TopicEntity topicEntity;
    @ManyToOne
    @JoinColumn(name="user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity userEntity;

    public MessageEntity(MessageFormDto messageFormDto) {
        BeanUtils.copyProperties(messageFormDto, this);
        this.creationDate = LocalDateTime.now();
    }

    public Message toMessage() {
        Message message = new Message();
        BeanUtils.copyProperties(this, message);
        message.setUser(this.userEntity.toUser());
        message.setTopic(this.topicEntity.toTopic());
        return message;
    }
}
