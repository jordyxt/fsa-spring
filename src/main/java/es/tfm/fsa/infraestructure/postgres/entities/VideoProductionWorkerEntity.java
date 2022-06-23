package es.tfm.fsa.infraestructure.postgres.entities;

import es.tfm.fsa.domain.model.VideoProductionWorker;
import es.tfm.fsa.domain.model.VideoProductionWorkerRole;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "workers")
public class VideoProductionWorkerEntity {
    @Id
    @GeneratedValue
    private int id;
    @NonNull
    @Column(nullable = false)
    private String name;
    private String description;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @JoinTable(
            name = "video_production_worker_role",
            joinColumns = @JoinColumn(name = "video_production_worker_id")
    )
    @Column(name = "role")
    private List<VideoProductionWorkerRole> videoProductionWorkerRoleList;

    public VideoProductionWorkerEntity(VideoProductionWorker videoProductionWorker) {
        BeanUtils.copyProperties(videoProductionWorker, this);
        this.setVideoProductionWorkerRoleList(videoProductionWorker.getVideoProductionWorkerRoleList());
    }

    public VideoProductionWorker toVideoProductionWorker() {
        VideoProductionWorker videoProductionWorker = new VideoProductionWorker();
        BeanUtils.copyProperties(this, videoProductionWorker);
        videoProductionWorker.setVideoProductionWorkerRoleList(this.getVideoProductionWorkerRoleList());
        return videoProductionWorker;
    }
}
