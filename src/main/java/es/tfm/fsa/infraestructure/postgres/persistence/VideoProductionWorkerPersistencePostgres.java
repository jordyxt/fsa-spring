package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.domain.exceptions.ConflictException;
import es.tfm.fsa.domain.exceptions.NotFoundException;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.VideoProductionWorker;
import es.tfm.fsa.domain.persistence.VideoProductionWorkerPersistence;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.GenreDao;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.VideoProductionWorkerDao;
import es.tfm.fsa.infraestructure.postgres.entities.GenreEntity;
import es.tfm.fsa.infraestructure.postgres.entities.VideoProductionWorkerEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class VideoProductionWorkerPersistencePostgres implements VideoProductionWorkerPersistence {
    private final VideoProductionWorkerDao videoProductionWorkerDao;

    @Autowired
    public VideoProductionWorkerPersistencePostgres(VideoProductionWorkerDao videoProductionWorkerDao) {
        this.videoProductionWorkerDao = videoProductionWorkerDao;
    }
    @Override
    public Optional<VideoProductionWorker> create(VideoProductionWorker videoProductionWorker) {
        VideoProductionWorkerEntity videoProductionWorkerEntity = new VideoProductionWorkerEntity(videoProductionWorker);
        return Optional.of(this.videoProductionWorkerDao.save(videoProductionWorkerEntity).toVideoProductionWorker());
    }

    @Override
    public Optional<VideoProductionWorker> findById(Integer id) {
        assertNameExist(id);
        return  this.videoProductionWorkerDao.findById(id).map(VideoProductionWorkerEntity::toVideoProductionWorker);
    }

    @Override
    public Optional<VideoProductionWorker> update(Integer id, VideoProductionWorker videoProductionWorker) {
        Optional<VideoProductionWorkerEntity> videoProductionWorkerEntityOptional;
        assertNameExist(id);
        videoProductionWorkerEntityOptional = this.videoProductionWorkerDao.findById(id);
        return videoProductionWorkerEntityOptional.map(videoProductionWorkerEntity ->
        {
            BeanUtils.copyProperties(videoProductionWorker, videoProductionWorkerEntity);
            return videoProductionWorkerEntity;
        }).map(this.videoProductionWorkerDao::save).map(VideoProductionWorkerEntity::toVideoProductionWorker);
    }

    @Override
    public Stream<VideoProductionWorker> findByNameContainingNullSafe(String name) {
        return this.videoProductionWorkerDao.findByNameContainingNullSafe(name).stream().
                map(VideoProductionWorkerEntity::toVideoProductionWorker);
    }

    @Override
    public Void delete(Integer id) {
        Optional<VideoProductionWorkerEntity> videoProductionWorkerEntityOptional;
        assertNameExist(id);
        videoProductionWorkerEntityOptional = this.videoProductionWorkerDao.findById(id);
        videoProductionWorkerEntityOptional.map(genreEntity ->{
                    this.videoProductionWorkerDao.deleteById(genreEntity.getId());
                    return null;
                }
        );
        return null;
    }

    private void assertNameExist(Integer id) {
        if (this.videoProductionWorkerDao.findById(id).isEmpty()) {
            throw new NotFoundException("Non existent videoProductionWorker id: " + id);
        }
    }
}
