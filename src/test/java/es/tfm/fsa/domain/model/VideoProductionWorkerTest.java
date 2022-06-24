package es.tfm.fsa.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VideoProductionWorkerTest {
    @Test
    void testDescriptionGenre() {
        VideoProductionWorker x = VideoProductionWorker.builder().name("testActor").description("d1").build();
        assertEquals("testActor", x.getName());
        assertEquals("d1", x.getDescription());
    }
}
