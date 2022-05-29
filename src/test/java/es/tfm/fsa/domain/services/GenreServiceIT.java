package es.tfm.fsa.domain.services;

import es.tfm.fsa.TestConfig;
import es.tfm.fsa.domain.exceptions.NotFoundException;
import es.tfm.fsa.domain.model.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
public class GenreServiceIT {
    @Autowired
    private GenreService genreService;
    @Test
    void testCreate() {
        this.genreService.create(Genre.builder().name("testGenreS1").description("d1").build());
        Optional<Genre> genre = this.genreService.read("testGenreS1");
        assertTrue(genre.isPresent());
        assertThat(genre.get().getName(), is("testGenreS1"));
    }
    @Test
    void testFindByNameAndDescription() {
        this.genreService.findByNameAndDescriptionContainingNullSafe(null,"description")
                .map(genre -> {
                    assertThat(genre.getDescription(), is("description"));
                    return true;
                });

    }
    @Test
    void testDelete() {
        this.genreService.create(Genre.builder().name("testGenreS2").description("d2").build());
        Optional<Genre> genre = this.genreService.read("testGenreS2");
        assertTrue(genre.isPresent());
        assertThat(genre.get().getName(), is("testGenreS2"));
        this.genreService.delete("testGenreS2");
        assertThrows(NotFoundException.class,()->this.genreService.read("testGenreS2"));
    }
}
