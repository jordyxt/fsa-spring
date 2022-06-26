package es.tfm.fsa.infraestructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.tfm.fsa.domain.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopicSearchDto {
    @NotBlank
    private int id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private String creationDate;
    private String username;
    private String videoProductionTitle;

    public TopicSearchDto(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.description = topic.getDescription();
        this.creationDate = topic.getCreationDate().
                format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy 'at' HH:mm", Locale.ENGLISH));
        this.username = topic.getUser().getUsername();
        this.videoProductionTitle = topic.getVideoProduction().getTitle();
    }
}
