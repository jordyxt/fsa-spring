package es.tfm.fsa.domain.services;

import es.tfm.fsa.domain.model.Message;
import es.tfm.fsa.domain.model.Topic;
import es.tfm.fsa.domain.persistence.MessagePersistence;
import es.tfm.fsa.domain.persistence.TopicPersistence;
import es.tfm.fsa.infraestructure.api.dtos.MessageFormDto;
import es.tfm.fsa.infraestructure.api.dtos.MessageSearchDto;
import es.tfm.fsa.infraestructure.api.dtos.TopicFormDto;
import es.tfm.fsa.infraestructure.api.dtos.TopicSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class MessageService {
    private MessagePersistence messagePersistence;
    @Autowired
    public MessageService(MessagePersistence messagePersistence){
        this.messagePersistence = messagePersistence;
    }
    public Optional<Message> create(MessageFormDto messageFormDto) {
        return this.messagePersistence.create(messageFormDto);
    }
    public Stream<MessageSearchDto> findByTopicIdNullSafe(Integer topicId) {
        return this.messagePersistence.findByTopicIdNullSafe(topicId).map(MessageSearchDto::new);
    }
}
