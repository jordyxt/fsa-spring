package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.domain.model.Message;
import es.tfm.fsa.domain.persistence.MessagePersistence;
import es.tfm.fsa.infraestructure.api.dtos.MessageFormDto;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.MessageDao;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.TopicDao;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.UserDao;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.VideoProductionDao;
import es.tfm.fsa.infraestructure.postgres.entities.MessageEntity;
import es.tfm.fsa.infraestructure.postgres.entities.TopicEntity;
import es.tfm.fsa.infraestructure.postgres.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class MessagePersistencePostgres implements MessagePersistence {
    private MessageDao messageDao;
    private UserDao userDao;
    private TopicDao topicDao;
    @Autowired
    public MessagePersistencePostgres(MessageDao messageDao, UserDao userDao, TopicDao topicDao) {
        this.messageDao = messageDao;
        this.userDao = userDao;
        this.topicDao = topicDao;
    }
    @Override
    public Optional<Message> create(MessageFormDto messageFormDto) {
        UserEntity userEntity = this.userDao.findByUsername(messageFormDto.getUsername()).get();
        TopicEntity topicEntity = this.topicDao.findById(messageFormDto.getTopicId()).get();
        MessageEntity messageEntity = new MessageEntity(messageFormDto);
        messageEntity.setUserEntity(userEntity);
        messageEntity.setTopicEntity(topicEntity);
        return Optional.of(this.messageDao.save(messageEntity).toMessage());
    }

    @Override
    public Stream<Message> findByTopicIdNullSafe(Integer topicId) {
        return  this.messageDao.findByTopicEntityId(topicId).stream().map(MessageEntity::toMessage);
    }
}
