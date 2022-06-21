package es.tfm.fsa.domain.services;

import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.Series;
import es.tfm.fsa.domain.persistence.SeriesPersistence;
import es.tfm.fsa.infraestructure.api.dtos.SeriesFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
public class SeriesService {
    private SeriesPersistence seriesPersistence;
    @Autowired
    public SeriesService(SeriesPersistence seriesPersistence){
        this.seriesPersistence = seriesPersistence;
    }
    public Optional<Series> create(SeriesFormDto seriesFormDto) {
        return this.seriesPersistence.create(seriesFormDto);
    }

    public Optional<Series> read(int id) {
        return this.seriesPersistence.findById(id);
    }

    public Stream<Series> findByTitleAndGenreListNullSafe(String title, List<String> genres) {
        return this.seriesPersistence.findByTitleNullSafe(title).filter(series ->
                (genres == null || genres.isEmpty() || series.getGenreList().stream().map(Genre::getName).
                        collect(Collectors.toList()).containsAll(genres)));
    }
}
