package es.tfm.fsa.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeriesTest {
    @Test
    void testDescriptionSeries() {
        Film x = Film.builder().title("testSeries").description("d1").build();
        assertEquals("testSeries", x.getTitle());
        assertEquals("d1", x.getDescription());
    }
}
