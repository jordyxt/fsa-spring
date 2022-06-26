package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.configuration.JwtService;
import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Topic;
import es.tfm.fsa.domain.services.FilmService;
import es.tfm.fsa.domain.services.TopicService;
import es.tfm.fsa.infraestructure.api.Rest;
import es.tfm.fsa.infraestructure.api.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Rest
@RequestMapping(TopicResource.TOPICS)
public class TopicResource {
    public static final String TOPICS = "/topics";
    public static final String SEARCH = "/search";
    public static final String ID = "/{id}";

    private TopicService topicService;
    private JwtService jwtService;

    @Autowired
    public TopicResource(TopicService topicService, JwtService jwtService){
        this.topicService = topicService;
        this.jwtService = jwtService;
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping(produces = {"application/json"})
    public Optional<Topic> create(@RequestHeader("Authorization") String token, @Valid @RequestBody TopicFormDto topicFormDto) {
        String extractedToken = this.jwtService.extractBearerToken(token);
        topicFormDto.setUsername(this.jwtService.user(extractedToken));
        return this.topicService.create(topicFormDto);
    }
    @PreAuthorize("permitAll()")
    @GetMapping(SEARCH)
    public Stream<TopicSearchDto> findByTitleAndGenreListNullSafe(
            @RequestParam(required = false) String title) {
        return this.topicService.findByTitleSafe(title);
    }
    @PreAuthorize("permitAll()")
    @GetMapping(ID)
    public Optional<TopicSearchDto> read(@PathVariable Integer id) {
        return this.topicService.read(id);
    }
}
