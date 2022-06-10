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
        this.filmService.findByTitleAndGenreListNullSafe(null, new ArrayList<String>(Arrays.asList("action","fantasy")))
                .map(genre -> {
                    assertThat(genre.getTitle(), is("Fantastic Beasts: The Secrets of Dumbledore"));
                    return true;
                });

    }
}
