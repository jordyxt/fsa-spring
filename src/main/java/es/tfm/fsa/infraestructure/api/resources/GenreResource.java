package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.services.GenreService;
import es.tfm.fsa.infraestructure.api.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Stream;

@Rest
@RequestMapping(GenreResource.GENRES)
public class GenreResource {
    public static final String GENRES = "/genres";
    public static final String SEARCH = "/search";

    private GenreService genreService;

    @Autowired
    public GenreResource(GenreService genreService){
        this.genreService = genreService;
    }
    @PostMapping(produces = {"application/json"})
    public Optional<Genre> create(@Valid @RequestBody Genre genre) {
        return this.genreService.create(genre);
    }
    @GetMapping(SEARCH)
    public Stream<Genre> findByNameAndDescriptionNullSafe(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description) {
        return this.genreService.findByNameAndDescriptionNullSafe(name, description)
                .map(Genre::ofNameDescription);
    }
}
