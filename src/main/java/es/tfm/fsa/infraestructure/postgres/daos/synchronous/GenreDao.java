package es.tfm.fsa.infraestructure.postgres.daos.synchronous;

import es.tfm.fsa.infraestructure.postgres.entities.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreDao extends JpaRepository<GenreEntity, Integer > {
    Optional<GenreEntity> findByName(String name);
}
