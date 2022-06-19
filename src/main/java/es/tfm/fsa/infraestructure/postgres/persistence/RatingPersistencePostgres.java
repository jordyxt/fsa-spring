package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.domain.model.Rating;
import es.tfm.fsa.domain.persistence.RatingPersistence;
import es.tfm.fsa.infraestructure.api.dtos.RatingFormDto;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.RatingDao;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.UserDao;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.VideoProductionDao;
import es.tfm.fsa.infraestructure.postgres.entities.RatingEntity;
import es.tfm.fsa.infraestructure.postgres.entities.UserEntity;
import es.tfm.fsa.infraestructure.postgres.entities.VideoProductionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class RatingPersistencePostgres implements RatingPersistence {
    private RatingDao ratingDao;
    private UserDao userDao;
    private VideoProductionDao videoProductionDao;
    @Autowired
    public RatingPersistencePostgres(RatingDao ratingDao, UserDao userDao, VideoProductionDao videoProductionDao) {
        this.ratingDao = ratingDao;
        this.userDao = userDao;
        this.videoProductionDao = videoProductionDao;
    }
    @Override
    public Optional<Rating> create(RatingFormDto ratingFormDto) {
        RatingEntity ratingEntity = new RatingEntity(ratingFormDto);
        UserEntity userEntity =this.userDao.findByUsername(ratingFormDto.getUsername()).get();
        ratingEntity.setUserEntity(userEntity);
        VideoProductionEntity videoProductionEntity = this.videoProductionDao.findById(ratingFormDto.getVideoProductionId()).get();
        ratingEntity.setVideoProductionEntity(videoProductionEntity);
        this.ratingDao.save(ratingEntity);
        return Optional.of(ratingEntity.toRating());
    }

    @Override
    public Stream<Rating> findByUsername(String username) {
        return this.ratingDao.findByUserEntityUsername(username).stream().
                map(RatingEntity::toRating);
    }

    @Override
    public Stream<Rating> findByVideoProductionId(int videoProductionId) {
        return this.ratingDao.findByVideoProductionEntityId(videoProductionId).stream().
                map(RatingEntity::toRating);
    }

    @Override
    public Optional<Rating> read(String username, Integer videoProductionId) {
        return this.ratingDao.findByUserEntityUsernameAndVideoProductionEntityId(username, videoProductionId).
                map(RatingEntity::toRating);
    }
}
