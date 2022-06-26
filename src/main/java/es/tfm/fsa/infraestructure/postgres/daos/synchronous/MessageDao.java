package es.tfm.fsa.infraestructure.postgres.daos.synchronous;

import es.tfm.fsa.infraestructure.postgres.entities.FilmEntity;
import es.tfm.fsa.infraestructure.postgres.entities.MessageEntity;
import es.tfm.fsa.infraestructure.postgres.entities.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MessageDao extends JpaRepository<MessageEntity, Integer > {

    List<MessageEntity> findByTopicEntityId(Integer topicId);

    Optional<MessageEntity> findById(Integer id);
}
