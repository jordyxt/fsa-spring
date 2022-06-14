package es.tfm.fsa.domain.services;

import es.tfm.fsa.TestConfig;
import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.infraestructure.api.dtos.FilmFormDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
public class FilmServiceIT {
    @Autowired
    private FilmService filmService;
    @Test
    void testFindByTitleAndGenreList() {
        this.filmService.findByTitleAndGenreListNullSafe(null, Arrays.asList("action","adventure","sci-fi"))
                .map(film -> {
                    System.out.println(film.toString());
                    assertThat(film.getTitle(), is("Jurassic World Dominion"));
                    return true;
                });

    }
    @Test
    void testCreate() {
        this.filmService.create(FilmFormDto.BBuilder().title("testFilmS1").description("d1").
                releaseDate(LocalDate.of(2022, Month.JANUARY,1)).
                genreList(Arrays.asList("action","adventure","sci-fi")).build());
        Optional<Film> film = this.filmService.findByTitleAndGenreListNullSafe("testFilmS1",null).findFirst();
        assertTrue(film.isPresent());
        assertThat(film.get().getTitle(), is("testFilmS1"));
        assertThat(film.get().getGenreList().stream().map(Genre::getName).collect(Collectors.toList()),
                is(Arrays.asList("action","adventure","sci-fi")));
    }
}
