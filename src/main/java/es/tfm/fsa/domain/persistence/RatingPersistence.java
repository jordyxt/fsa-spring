package es.tfm.fsa.domain.persistence;

import es.tfm.fsa.domain.model.Rating;
import es.tfm.fsa.infraestructure.api.dtos.RatingFormDto;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface RatingPersistence {
    Optional<Integer> create(RatingFormDto ratingFormDto);

    Stream<Rating> findByUsername(String username);

    Stream<Rating> findByVideoProductionId(Integer videoProductionId);

    Optional<Integer> read(String username, Integer videoProductionId);
}
