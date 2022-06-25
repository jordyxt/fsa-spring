package es.tfm.fsa.domain.services;

import es.tfm.fsa.domain.model.*;
import es.tfm.fsa.domain.persistence.FilmPersistence;
import es.tfm.fsa.domain.persistence.RatingPersistence;
import es.tfm.fsa.domain.persistence.TopicPersistence;
import es.tfm.fsa.infraestructure.api.dtos.FilmFormDto;
import es.tfm.fsa.infraestructure.api.dtos.FilmSearchDto;
import es.tfm.fsa.infraestructure.api.dtos.TopicFormDto;
import es.tfm.fsa.infraestructure.api.dtos.TopicSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class TopicService {
    private TopicPersistence topicPersistence;
    @Autowired
    public TopicService(TopicPersistence topicPersistence){
        this.topicPersistence = topicPersistence;
    }
    public Optional<Topic> create(TopicFormDto topicFormDto) {
        return this.topicPersistence.create(topicFormDto);
    }
    public Optional<Topic> findById(int id) {
        return this.topicPersistence.findById(id);
    }
    public Optional<Topic> findByTitle(String title) {
        return this.topicPersistence.findByTitle(title);
    }
    public Stream<TopicSearchDto> findByTitleSafe(String title) {
        return this.topicPersistence.findByTitleNullSafe(title).map(TopicSearchDto::new);
    }
}
