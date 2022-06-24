package es.tfm.fsa.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoProductionWorker {
    private int id;
    @NotBlank
    private String name;
    private String description;
    private LocalDate birthdate;
    @Enumerated(EnumType.STRING)
    private List<VideoProductionWorkerRole> videoProductionWorkerRoleList;

    public static VideoProductionWorker ofNameDescription(VideoProductionWorker videoProductionWorker) {
        return VideoProductionWorker.builder()
                .id(videoProductionWorker.getId())
                .name(videoProductionWorker.getName())
                .description(videoProductionWorker.getDescription())
                .birthdate(videoProductionWorker.getBirthdate())
                .build();
    }
}
