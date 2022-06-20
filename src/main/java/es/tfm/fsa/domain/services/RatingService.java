package es.tfm.fsa.domain.services;

import es.tfm.fsa.domain.model.Rating;
import es.tfm.fsa.domain.persistence.RatingPersistence;
import es.tfm.fsa.infraestructure.api.dtos.RatingFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {
    private RatingPersistence ratingPersistence;
    @Autowired
    public RatingService(RatingPersistence ratingPersistence){
        this.ratingPersistence = ratingPersistence;
    }
    public Optional<Integer> create(RatingFormDto ratingFormDto) {
        return this.ratingPersistence.create(ratingFormDto);
    }

    public Optional<Integer> read(String username, Integer videoProductionId) {
        return this.ratingPersistence.read(username, videoProductionId);
    }
}
