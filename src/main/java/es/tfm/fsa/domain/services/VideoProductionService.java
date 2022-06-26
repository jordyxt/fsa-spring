package es.tfm.fsa.domain.services;

import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.Rating;
import es.tfm.fsa.domain.model.VideoProduction;
import es.tfm.fsa.domain.persistence.RatingPersistence;
import es.tfm.fsa.domain.persistence.VideoProductionPersistence;
import es.tfm.fsa.infraestructure.api.dtos.VideoProductionMyListSearchDto;
import es.tfm.fsa.infraestructure.api.dtos.VideoProductionSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class VideoProductionService {
    private final VideoProductionPersistence videoProductionPersistence;
    private final RatingPersistence ratingPersistence;

    @Autowired
    public VideoProductionService(VideoProductionPersistence videoProductionPersistence,
                                  RatingPersistence ratingPersistence) {
        this.videoProductionPersistence = videoProductionPersistence;
        this.ratingPersistence = ratingPersistence;
    }

    public Stream<VideoProductionSearchDto> findByTitleAndGenreListNullSafe(String title, List<String> genres) {
        return this.videoProductionPersistence.findByTitleNullSafe(title).filter(videoProduction ->
                (genres == null || genres.isEmpty() ||
                        videoProduction.getGenreList().stream().map(Genre::getName).
                                collect(Collectors.toList()).containsAll(genres))).
                map(VideoProductionSearchDto::new).map(videoProductionSearchDto -> {
            videoProductionSearchDto.setRating(this.ratingPersistence.
                    findByVideoProductionId(videoProductionSearchDto.getId()).
                    mapToDouble(Rating::getRating).average().orElse(0.0));
            return videoProductionSearchDto;
        }).sorted(Comparator.comparing(VideoProductionSearchDto::getRating).reversed());
    }

    public Optional<VideoProduction> findById(Integer id) {
        return this.videoProductionPersistence.findById(id);
    }

    public Stream<VideoProductionMyListSearchDto> findByTitleAndUsernameList(String title, String username) {
        return this.videoProductionPersistence.findByTitleNullSafe(title).filter(videoProduction ->
                this.ratingPersistence.read(username,
                        videoProduction.getId()).isPresent()).
                map(VideoProductionMyListSearchDto::new).map(videoProductionMyListSearchDto -> {
            videoProductionMyListSearchDto.setRating(this.ratingPersistence.
                    findByVideoProductionId(videoProductionMyListSearchDto.getId()).
                    mapToDouble(Rating::getRating).average().orElse(0.0));
            videoProductionMyListSearchDto.setMyRating(this.ratingPersistence.read(username,
                    videoProductionMyListSearchDto.getId()).orElse(0));
            return videoProductionMyListSearchDto;
        }).sorted(Comparator.comparing(VideoProductionMyListSearchDto::getRating).reversed());
    }
}

