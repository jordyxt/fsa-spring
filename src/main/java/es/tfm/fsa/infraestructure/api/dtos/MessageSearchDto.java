package es.tfm.fsa.infraestructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.tfm.fsa.domain.model.Message;
import es.tfm.fsa.domain.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageSearchDto {
    @NotBlank
    private String message;
    private String creationDate;
    private String username;
    private Integer topicId;
    public MessageSearchDto(Message message) {
        this.message = message.getMessage();
        this.creationDate = message.getCreationDate().
                format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy 'at' HH:mm", Locale.ENGLISH));
        this.username = message.getUser().getUsername();
        this.topicId = message.getTopic().getId();
    }
}
