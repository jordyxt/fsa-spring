package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.domain.model.VideoProduction;
import es.tfm.fsa.domain.persistence.VideoProductionPersistence;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.VideoProductionDao;
import es.tfm.fsa.infraestructure.postgres.entities.VideoProductionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;
@Repository
public class VideoProductionPersistencePostgres implements VideoProductionPersistence {
    private VideoProductionDao videoProductionDao;
    @Autowired
    public VideoProductionPersistencePostgres(VideoProductionDao videoProductionDao) {
        this.videoProductionDao = videoProductionDao;
    }
    @Override
    public Stream<VideoProduction> findByTitleNullSafe(String title) {
        return this.videoProductionDao.findByTitleContaining(title).stream().map(VideoProductionEntity::toVideoProduction);
    }
}
