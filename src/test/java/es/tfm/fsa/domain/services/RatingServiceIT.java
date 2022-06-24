package es.tfm.fsa.domain.services;

import es.tfm.fsa.TestConfig;
import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.Rating;
import es.tfm.fsa.infraestructure.api.dtos.FilmFormDto;
import es.tfm.fsa.infraestructure.api.dtos.FilmSearchDto;
import es.tfm.fsa.infraestructure.api.dtos.RatingFormDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
@TestConfig
public class RatingServiceIT {
    @Autowired
    private RatingService ratingService;
    @Autowired
    private FilmService filmService;
    @Test
    void testCreate() {
        this.filmService.create(FilmFormDto.BBuilder().title("testRateS1").description("d1").
                releaseDate(LocalDate.of(2022, Month.JANUARY,1)).
                genreList(Arrays.asList("action","adventure","sci-fi")).build());
        Optional<FilmSearchDto> filmSearchDto = this.filmService.findByTitleAndGenreListNullSafe("testRateS1",null, null).findFirst();
        this.ratingService.create(RatingFormDto.builder().rating(7).username("admin").videoProductionId(filmSearchDto.get().getId()).build());
        Optional<Integer> rating = this.ratingService.read("admin",filmSearchDto.get().getId());
        assertTrue(rating.isPresent());
        assertThat(rating.get(), is(7));
    }
}
