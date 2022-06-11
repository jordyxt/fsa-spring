package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.domain.model.Series;
import es.tfm.fsa.domain.persistence.SeriesPersistence;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.FilmDao;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.SeriesDao;
import es.tfm.fsa.infraestructure.postgres.entities.FilmEntity;
import es.tfm.fsa.infraestructure.postgres.entities.SeriesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class SeriesPersistencePostgres implements SeriesPersistence {
    private SeriesDao seriesDao;

    @Autowired
    public SeriesPersistencePostgres(SeriesDao seriesDao) {
        this.seriesDao = seriesDao;
    }
    @Override
    public Optional<Series> create(Series series) {
        return Optional.empty();
    }

    @Override
    public Optional<Series> findById(int id) {
        return this.seriesDao.findById(id).map(SeriesEntity::toSeries);
    }

    @Override
    public Optional<Series> update(int id, Series series) {
        return Optional.empty();
    }

    @Override
    public Stream<Series> findByTitleNullSafe(String title) {
        return this.seriesDao.findByTitleContaining(title).stream().map(SeriesEntity::toSeries);
    }

    @Override
    public Void delete(String name) {
        return null;
    }
}
