package es.tfm.fsa.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmTest {
    @Test
    void testDescriptionFilm() {
        Film x = Film.BBuilder().title("testFilm").description("d1").build();
        assertEquals("testFilm", x.getTitle());
        assertEquals("d1", x.getDescription());
    }
}
