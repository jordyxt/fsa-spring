package es.tfm.fsa.domain.persistence;

import es.tfm.fsa.domain.model.Message;
import es.tfm.fsa.infraestructure.api.dtos.MessageFormDto;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface MessagePersistence {
    Optional<Message> create(MessageFormDto messageFormDto);

    Stream<Message> findByTopicIdNullSafe(Integer topicId);
}
