package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.configuration.JwtService;
import es.tfm.fsa.domain.model.Message;
import es.tfm.fsa.domain.model.Topic;
import es.tfm.fsa.domain.services.MessageService;
import es.tfm.fsa.domain.services.TopicService;
import es.tfm.fsa.infraestructure.api.Rest;
import es.tfm.fsa.infraestructure.api.dtos.MessageFormDto;
import es.tfm.fsa.infraestructure.api.dtos.MessageSearchDto;
import es.tfm.fsa.infraestructure.api.dtos.TopicFormDto;
import es.tfm.fsa.infraestructure.api.dtos.TopicSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Stream;

@Rest
@RequestMapping(MessageResource.MESSAGES)
public class MessageResource {
    public static final String MESSAGES = "/messages";
    public static final String SEARCH = "/search";

    private MessageService messageService;
    private JwtService jwtService;

    @Autowired
    public MessageResource(MessageService messageService, JwtService jwtService){
        this.messageService = messageService;
        this.jwtService = jwtService;
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping(produces = {"application/json"})
    public Optional<Message> create(@RequestHeader("Authorization") String token,
                                    @Valid @RequestBody MessageFormDto messageFormDto) {
        String extractedToken = this.jwtService.extractBearerToken(token);
        messageFormDto.setUsername(this.jwtService.user(extractedToken));
        return this.messageService.create(messageFormDto);
    }
    @PreAuthorize("permitAll()")
    @GetMapping(SEARCH)
    public Stream<MessageSearchDto> findByTitleAndGenreListNullSafe(
            @RequestParam(required = false) Integer topicId) {
        return this.messageService.findByTopicIdNullSafe(topicId);
    }
}
