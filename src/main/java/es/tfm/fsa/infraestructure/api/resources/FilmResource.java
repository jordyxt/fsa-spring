package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.services.FilmService;
import es.tfm.fsa.infraestructure.api.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.stream.Stream;

@Rest
@RequestMapping(FilmResource.FILMS)
public class FilmResource {
    public static final String FILMS = "/films";
    public static final String SEARCH = "/search";
    public static final String PICTURES = "/pictures";
    public static final String ID = "/{id}";

    private FilmService filmService;

    @Autowired
    public FilmResource(FilmService filmService){
        this.filmService = filmService;
    }
    @GetMapping(SEARCH)
    public Stream<Film> findByNameAndDescriptionContainingNullSafe(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Collection<String> genres) {
        return this.filmService.findByTitleAndGenreListNullSafe(title, genres);
    }
    @GetMapping(PICTURES+ID)
    public ResponseEntity<Byte[]> getPicture(@PathVariable Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        Byte[] media = this.filmService.read(id).get().getPoster();

        ResponseEntity<Byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;
    }
}
