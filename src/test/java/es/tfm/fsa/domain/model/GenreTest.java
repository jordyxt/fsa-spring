package es.tfm.fsa.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenreTest {
    @Test
    void testDescriptionGenre() {
        Genre x = Genre.builder().name("testGenre").description("d").build();
        assertEquals("testGenre", x.getName());
        assertEquals("d", x.getDescription());
    }
}
