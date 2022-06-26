package es.tfm.fsa.domain.persistence;

import es.tfm.fsa.domain.model.Role;
import es.tfm.fsa.domain.model.Topic;
import es.tfm.fsa.domain.model.User;
import es.tfm.fsa.infraestructure.api.dtos.TopicFormDto;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface TopicPersistence {
    Optional<Topic> create(TopicFormDto topicFormDto);

    Optional<Topic> findById(Integer id);

    Optional<Topic> findByTitle(String title);

    Stream<Topic> findByTitleNullSafe(String title);
}
