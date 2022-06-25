package es.tfm.fsa.infraestructure.postgres.daos.synchronous;

import es.tfm.fsa.infraestructure.postgres.entities.FilmEntity;
import es.tfm.fsa.infraestructure.postgres.entities.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicDao extends JpaRepository<TopicEntity, Integer > {
    @Query("select t from TopicEntity t where " +
            "(?1 is null or lower(t.title) like lower(concat('%', cast( ?1 as string),'%')))")
    List<TopicEntity> findByTitleContaining(String title);
}
