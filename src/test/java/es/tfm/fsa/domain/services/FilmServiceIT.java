package es.tfm.fsa.domain.services;

import es.tfm.fsa.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
}
