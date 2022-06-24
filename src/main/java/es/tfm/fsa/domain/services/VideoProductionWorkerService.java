package es.tfm.fsa.domain.services;

import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.VideoProductionWorker;
import es.tfm.fsa.domain.persistence.GenrePersistence;
import es.tfm.fsa.domain.persistence.VideoProductionWorkerPersistence;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;
@Service
public class VideoProductionWorkerService {

    private VideoProductionWorkerPersistence videoProductionWorkerPersistence;

    @Autowired
    public VideoProductionWorkerService(VideoProductionWorkerPersistence videoProductionWorkerPersistence){
        this.videoProductionWorkerPersistence = videoProductionWorkerPersistence;
    }
    public Optional<VideoProductionWorker> create(VideoProductionWorker videoProductionWorker) {
        return this.videoProductionWorkerPersistence.create(videoProductionWorker);
    }

    public Optional<VideoProductionWorker> read(Integer id) {
        return this.videoProductionWorkerPersistence.findById(id);
    }

    public Stream<VideoProductionWorker> findByNameContainingNullSafe(String name) {
        return this.videoProductionWorkerPersistence.findByNameContainingNullSafe(name);
    }
    public Optional<VideoProductionWorker> update(Integer id, VideoProductionWorker videoProductionWorker) {
        return this.videoProductionWorkerPersistence.findById(id)
                .map(dataVideoProductionWorker -> {
                    BeanUtils.copyProperties(videoProductionWorker, dataVideoProductionWorker);
                    return dataVideoProductionWorker;
                }).flatMap(dataVideoProductionWorker -> this.videoProductionWorkerPersistence.
                        update(id, dataVideoProductionWorker));
    }

    public Void delete(Integer id) {
        return this.videoProductionWorkerPersistence.delete(id);
    }
}
