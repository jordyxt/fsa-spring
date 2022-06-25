package es.tfm.fsa.domain.services;

import es.tfm.fsa.TestConfig;
import es.tfm.fsa.domain.model.Topic;
import es.tfm.fsa.infraestructure.api.dtos.FilmFormDto;
import es.tfm.fsa.infraestructure.api.dtos.FilmSearchDto;
import es.tfm.fsa.infraestructure.api.dtos.TopicFormDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
public class TopicServiceIT {
    @Autowired
    private TopicService topicService;
    @Autowired
    private FilmService filmService;
    @Test
    void testCreate() {
        this.filmService.create(FilmFormDto.BBuilder().title("testTopicS1").description("d1").
                releaseDate(LocalDate.of(2022, Month.JANUARY,1)).
                genreList(Arrays.asList("action","adventure","sci-fi")).
                directorList(Arrays.asList()).actorList(Arrays.asList())
                .build());
        Optional<FilmSearchDto> filmSearchDto = this.filmService.
                findByTitleAndGenreListNullSafe("testTopicS1",null, null).findFirst();
        this.topicService.create(TopicFormDto.builder().title("TopicTitle1").
                description("description").username("admin").
                videoProductionTitle(filmSearchDto.get().getTitle()).build());
        Optional<Topic> topic = this.topicService.findByTitle("TopicTitle1");
        assertTrue(topic.isPresent());
        assertThat(topic.get().getDescription(), is("description"));
    }
}
