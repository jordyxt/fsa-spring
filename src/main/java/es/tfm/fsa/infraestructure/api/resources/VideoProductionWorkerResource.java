package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.VideoProductionWorker;
import es.tfm.fsa.domain.services.GenreService;
import es.tfm.fsa.domain.services.VideoProductionWorkerService;
import es.tfm.fsa.infraestructure.api.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Stream;
@Rest
@RequestMapping(VideoProductionWorkerResource.WORKERS)
public class VideoProductionWorkerResource {
    public static final String WORKERS = "/workers";
    public static final String SEARCH = "/search";
    public static final String ID = "/{id}";

    private VideoProductionWorkerService videoProductionWorkerService;

    @Autowired
    public VideoProductionWorkerResource(VideoProductionWorkerService videoProductionWorkerService){
        this.videoProductionWorkerService = videoProductionWorkerService;
    }
    @PostMapping(produces = {"application/json"})
    public Optional<VideoProductionWorker> create(@Valid @RequestBody VideoProductionWorker videoProductionWorker) {
        return this.videoProductionWorkerService.create(videoProductionWorker);
    }
    @PreAuthorize("permitAll()")
    @GetMapping(SEARCH)
    public Stream<VideoProductionWorker> findByNameAndDescriptionContainingNullSafe(
            @RequestParam(required = false) String name) {
        return this.videoProductionWorkerService.findByNameContainingNullSafe(name)
                .map(VideoProductionWorker::ofNameDescription);
    }
    @PutMapping(ID)
    public Optional<VideoProductionWorker> update(@PathVariable Integer id, @Valid @RequestBody VideoProductionWorker videoProductionWorker) {
        return this.videoProductionWorkerService.update(id, videoProductionWorker);
    }

    @DeleteMapping(ID)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Void delete(@PathVariable Integer id) {
        return this.videoProductionWorkerService.delete(id);
    }
    @PreAuthorize("permitAll()")
    @GetMapping(ID)
    public Optional<VideoProductionWorker> read(@PathVariable Integer id) {
        return this.videoProductionWorkerService.read(id);
    }
}
