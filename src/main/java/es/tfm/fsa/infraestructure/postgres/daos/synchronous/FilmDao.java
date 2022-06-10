package es.tfm.fsa.infraestructure.postgres.daos.synchronous;

import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.infraestructure.postgres.entities.FilmEntity;
import es.tfm.fsa.infraestructure.postgres.entities.GenreEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Repository
public interface FilmDao extends JpaRepository<FilmEntity, Integer > {
    Optional<FilmEntity> findById(int id);

    Optional<FilmEntity> findByTitle(String title);

    @Query("select f from FilmEntity f where " +
            "(?1 is null or lower(f.title) like lower(concat('%', cast( ?1 as string),'%')))")
    List<FilmEntity> findByTitleContaining(String title);
}
