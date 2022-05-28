package es.tfm.fsa.infraestructure.postgres.daos.synchronous;

import es.tfm.fsa.infraestructure.postgres.entities.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GenreDao extends JpaRepository<GenreEntity, Integer > {
    Optional<GenreEntity> findByName(String name);
    @Query("select g from GenreEntity g where " +
            "(?1 is null or lower(g.name) like lower(concat('%', cast( ?1 as string),'%'))) and" +
            "(?2 is null or lower(g.description) like lower(concat('%', cast( ?2 as string),'%')))")
    List<GenreEntity> findByNameAndDescriptionContainingNullSafe(String name, String description);
}
