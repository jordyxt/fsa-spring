package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.services.FilmService;
import es.tfm.fsa.infraestructure.api.Rest;
import es.tfm.fsa.infraestructure.api.dtos.FilmFormDto;
import es.tfm.fsa.infraestructure.api.dtos.FilmReviewDto;
import es.tfm.fsa.infraestructure.api.dtos.FilmSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
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
    @PostMapping(produces = {"application/json"})
    public Optional<Film> create(@Valid @RequestBody FilmFormDto filmFormDto) {
        filmFormDto.doDefault();
        return this.filmService.create(filmFormDto);
    }
    @PreAuthorize("permitAll()")
    @GetMapping(SEARCH)
    public Stream<FilmSearchDto> findByTitleAndGenreListNullSafe(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<String> genreList) {
        return this.filmService.findByTitleAndGenreListNullSafe(title,genreList);
    }
    @PreAuthorize("permitAll()")
    @GetMapping(PICTURES+ID)
    public ResponseEntity<byte[]> getPicture(@PathVariable Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE));
        byte[] media = this.filmService.findById(id).get().getPoster();
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;
    }
    @PreAuthorize("permitAll()")
    @GetMapping(ID)
    public Optional<FilmReviewDto> read(@PathVariable Integer id) {
        return this.filmService.read(id);
    }
}
