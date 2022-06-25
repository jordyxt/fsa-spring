package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.domain.model.Topic;
import es.tfm.fsa.domain.persistence.TopicPersistence;
import es.tfm.fsa.infraestructure.api.dtos.TopicFormDto;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.TopicDao;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.UserDao;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.VideoProductionDao;
import es.tfm.fsa.infraestructure.postgres.entities.FilmEntity;
import es.tfm.fsa.infraestructure.postgres.entities.TopicEntity;
import es.tfm.fsa.infraestructure.postgres.entities.UserEntity;
import es.tfm.fsa.infraestructure.postgres.entities.VideoProductionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class TopicPersistencePostgres implements TopicPersistence {
    private TopicDao topicDao;
    private UserDao userDao;
    private VideoProductionDao videoProductionDao;
    @Autowired
    public TopicPersistencePostgres(TopicDao topicDao, UserDao userDao, VideoProductionDao videoProductionDao) {
        this.topicDao = topicDao;
        this.userDao = userDao;
        this.videoProductionDao = videoProductionDao;
    }
    @Override
    public Optional<Topic> create(TopicFormDto topicFormDto) {
        UserEntity userEntity = this.userDao.findByUsername(topicFormDto.getUsername()).get();
        VideoProductionEntity videoProductionEntity = this.videoProductionDao.findByTitle(topicFormDto.getVideoProductionTitle()).get();
        TopicEntity topicEntity = new TopicEntity(topicFormDto);
        topicEntity.setUserEntity(userEntity);
        topicEntity.setVideoProductionEntity(videoProductionEntity);
        return Optional.of(this.topicDao.save(topicEntity).toTopic());
    }

    @Override
    public Optional<Topic> findById(int id) {
        return this.topicDao.findById(id).map(TopicEntity::toTopic);
    }

    @Override
    public Stream<Topic> findByTitleNullSafe(String title) {
        return this.topicDao.findByTitleContaining(title).stream().map(TopicEntity::toTopic);
    }
}
