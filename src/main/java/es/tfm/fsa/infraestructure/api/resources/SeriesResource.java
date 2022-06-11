package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.domain.services.FilmService;
import es.tfm.fsa.domain.services.SeriesService;
import es.tfm.fsa.infraestructure.api.Rest;
import es.tfm.fsa.infraestructure.api.dtos.FilmSearchDto;
import es.tfm.fsa.infraestructure.api.dtos.SeriesSearchDto;
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
@RequestMapping(SeriesResource.SERIES)
public class SeriesResource {
    public static final String SERIES = "/series";
    public static final String SEARCH = "/search";
    public static final String PICTURES = "/pictures";
    public static final String ID = "/{id}";
    public static final String TITLE = "/{title}";

    private SeriesService seriesService;

    @Autowired
    public SeriesResource(SeriesService seriesService){
        this.seriesService = seriesService;
    }
    @PreAuthorize("permitAll()")
    @GetMapping(SEARCH)
    public Stream<SeriesSearchDto> findByTitleAndGenreListNullSafe(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<String> genreList) {
        return this.seriesService.findByTitleAndGenreListNullSafe(title,genreList).map(SeriesSearchDto::new);
    }
    @PreAuthorize("permitAll()")
    @GetMapping(PICTURES+ID)
    public ResponseEntity<byte[]> getPicture(@PathVariable Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE));
        byte[] media = this.seriesService.read(id).get().getPoster();
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;
    }
}
