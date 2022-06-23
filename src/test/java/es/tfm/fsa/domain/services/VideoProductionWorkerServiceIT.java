package es.tfm.fsa.domain.services;

import es.tfm.fsa.TestConfig;
import es.tfm.fsa.domain.exceptions.NotFoundException;
import es.tfm.fsa.domain.model.VideoProductionWorker;
import es.tfm.fsa.domain.model.VideoProductionWorkerRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
@TestConfig
public class VideoProductionWorkerServiceIT {
    @Autowired
    private VideoProductionWorkerService videoProductionWorkerService;
    @Test
    void testCreate() {
        Optional<VideoProductionWorker> actor = this.videoProductionWorkerService.create(VideoProductionWorker.
                builder().name("testActorS1").description("d1").
                videoProductionWorkerRoleList(Arrays.asList(VideoProductionWorkerRole.ACTOR,VideoProductionWorkerRole.DIRECTOR)).build());
        assertTrue(actor.isPresent());
        assertThat(actor.get().getName(), is("testActorS1"));
    }
    @Test
    void testFindByName() {
        this.videoProductionWorkerService.create(VideoProductionWorker.
                builder().name("testActorS2").description("d1").build());
        this.videoProductionWorkerService.findByNameContainingNullSafe("testActorS2")
                .map(videoProductionWorker -> {
                    assertThat(videoProductionWorker.getDescription(), is("description"));
                    return true;
                });

    }
    @Test
    void testDelete() {
        Optional<VideoProductionWorker> actor = this.videoProductionWorkerService.
                create(VideoProductionWorker.builder().
                name("testActorS3").description("d3").build());
        assertTrue(actor.isPresent());
        assertThat(actor.get().getName(), is("testActorS3"));
        this.videoProductionWorkerService.delete(actor.get().getId());
        assertThrows(NotFoundException.class,()->this.videoProductionWorkerService.read(actor.get().getId()));
    }
}
