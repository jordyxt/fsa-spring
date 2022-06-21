package es.tfm.fsa.infraestructure.postgres.daos.synchronous;

import es.tfm.fsa.infraestructure.postgres.entities.FilmEntity;
import es.tfm.fsa.infraestructure.postgres.entities.SeriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
@Transactional
public interface SeriesDao extends JpaRepository<SeriesEntity, Integer > {
    Optional<SeriesEntity> findById(int id);

    @Query("select s from SeriesEntity s where " +
            "(?1 is null or lower(s.title) like lower(concat('%', cast( ?1 as string),'%')))")
    List<SeriesEntity> findByTitleContaining(String title);
}
