package es.tfm.fsa.domain.services;

import es.tfm.fsa.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestConfig
public class SeriesServiceIT {
    @Autowired
    private SeriesService seriesService;
    @Test
    void testFindByTitleAndGenreList() {
        this.seriesService.findByTitleAndGenreListNullSafe(null, Arrays.asList("crime"))
                .map(film -> {
                    System.out.println(film.toString());
                    assertThat(film.getTitle(), is("Money Heist"));
                    return true;
                });

    }
}
