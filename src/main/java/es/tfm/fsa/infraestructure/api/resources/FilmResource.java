package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.services.FilmService;
import es.tfm.fsa.domain.services.GenreService;
import es.tfm.fsa.infraestructure.api.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.stream.Stream;

@Rest
@RequestMapping(FilmResource.FILMS)
public class FilmResource {
    public static final String FILMS = "/films";
    public static final String SEARCH = "/search";
    public static final String NAME_ID = "/{name}";

    private FilmService filmService;

    @Autowired
    public FilmResource(FilmService filmService){
        this.filmService = filmService;
    }
    @GetMapping(SEARCH)
    public Stream<Film> findByNameAndDescriptionContainingNullSafe(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Collection<String> genres) {
        return this.filmService.findByNameAndDescriptionContainingNullSafe(title, genres);
    }
}
