package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.services.FilmService;
import es.tfm.fsa.infraestructure.api.Rest;
import es.tfm.fsa.infraestructure.api.dtos.FilmSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Stream;

@Rest
@RequestMapping(FilmResource.FILMS)
public class FilmResource {
    public static final String FILMS = "/films";
    public static final String SEARCH = "/search";
    public static final String PICTURES = "/pictures";
    public static final String ID = "/{id}";
    public static final String TITLE = "/{title}";

    private FilmService filmService;

    @Autowired
    public FilmResource(FilmService filmService){
        this.filmService = filmService;
    }
    @PreAuthorize("permitAll()")
    @GetMapping(SEARCH)
    public Stream<FilmSearchDto> findByTitleAndGenreListNullSafe(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<String> genreList) {
        return this.filmService.findByTitleAndGenreListNullSafe(title,genreList).map(FilmSearchDto::new);
    }
    @PreAuthorize("permitAll()")
    @GetMapping(PICTURES+ID)
    public ResponseEntity<byte[]> getPicture(@PathVariable Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE));
        byte[] media = this.filmService.read(id).get().getPoster();
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;
    }
}
