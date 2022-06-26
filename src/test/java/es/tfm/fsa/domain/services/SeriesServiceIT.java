package es.tfm.fsa.domain.services;

import es.tfm.fsa.TestConfig;
import es.tfm.fsa.infraestructure.api.dtos.SeriesFormDto;
import es.tfm.fsa.infraestructure.api.dtos.SeriesSearchDto;
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
public class SeriesServiceIT {
    @Autowired
    private SeriesService seriesService;

    @Test
    void testFindByTitleAndGenreList() {
        this.seriesService.findByTitleAndGenreListNullSafe(null, Collections.singletonList("crime"), null)
                .map(film -> {
                    System.out.println(film.toString());
                    assertThat(film.getTitle(), is("Money Heist"));
                    return true;
                });

    }

    @Test
    void testCreate() {
        this.seriesService.create(SeriesFormDto.BBuilder().title("testSeriesS1").description("d1").
                releaseDate(LocalDate.of(2022, Month.JANUARY, 1)).
                genreList(Arrays.asList("action", "adventure", "sci-fi")).
                directorList(Collections.emptyList()).actorList(Collections.emptyList())
                .build());
        Optional<SeriesSearchDto> seriesSearchDto = this.seriesService.findByTitleAndGenreListNullSafe("testSeriesS1", null, null).findFirst();
        assertTrue(seriesSearchDto.isPresent());
        assertThat(seriesSearchDto.get().getTitle(), is("testSeriesS1"));
        assertThat(new ArrayList<>(seriesSearchDto.get().getGenreList()),
                is(Arrays.asList("action", "adventure", "sci-fi")));
    }
}
