package es.tfm.fsa.infraestructure.postgres.persistence;

import es.tfm.fsa.domain.exceptions.NotFoundException;
import es.tfm.fsa.domain.model.Series;
import es.tfm.fsa.domain.persistence.SeriesPersistence;
import es.tfm.fsa.infraestructure.api.dtos.SeriesFormDto;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.FilmDao;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.GenreDao;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.SeriesDao;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.VideoProductionWorkerDao;
import es.tfm.fsa.infraestructure.postgres.entities.FilmEntity;
import es.tfm.fsa.infraestructure.postgres.entities.SeriesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class SeriesPersistencePostgres implements SeriesPersistence {
    private SeriesDao seriesDao;
    private GenreDao genreDao;
    private VideoProductionWorkerDao videoProductionWorkerDao;

    @Autowired
    public SeriesPersistencePostgres(SeriesDao seriesDao, GenreDao genreDao, VideoProductionWorkerDao videoProductionWorkerDao) {
        this.seriesDao = seriesDao;
        this.genreDao = genreDao;
        this.videoProductionWorkerDao = videoProductionWorkerDao;
    }
    @Override
    public Optional<Series> create(SeriesFormDto seriesFormDto) {
        SeriesEntity seriesEntity = new SeriesEntity(seriesFormDto);
        seriesFormDto.getGenreList().stream().
                map(name -> {
                    if (this.genreDao.findByName(name).isEmpty()) {
                        throw new NotFoundException("Non existent genre name: " + name);
                    }
                    return this.genreDao.findByName(name).get();
                }).
                forEach(seriesEntity::add);
        seriesFormDto.getDirectorList().stream().
                map(name -> {
                    if (this.videoProductionWorkerDao.findByName(name).isEmpty()) {
                        throw new NotFoundException("Non existent director name: " + name);
                    }
                    return this.videoProductionWorkerDao.findByName(name).get();
                }).
                forEach(seriesEntity::addDirector);
        seriesFormDto.getActorList().stream().
                map(name -> {
                    if (this.videoProductionWorkerDao.findByName(name).isEmpty()) {
                        throw new NotFoundException("Non existent actor name: " + name);
                    }
                    return this.videoProductionWorkerDao.findByName(name).get();
                }).
                forEach(seriesEntity::addActor);
        return Optional.of(this.seriesDao.save(seriesEntity).toSeries());
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
