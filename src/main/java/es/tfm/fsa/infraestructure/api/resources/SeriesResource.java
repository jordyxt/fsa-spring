package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Series;
import es.tfm.fsa.domain.services.SeriesService;
import es.tfm.fsa.infraestructure.api.Rest;
import es.tfm.fsa.infraestructure.api.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Rest
@RequestMapping(SeriesResource.SERIES)
public class SeriesResource {
    public static final String SERIES = "/series";
    public static final String SEARCH = "/search";
    public static final String PICTURES = "/pictures";
    public static final String ID = "/{id}";

    private SeriesService seriesService;

    @Autowired
    public SeriesResource(SeriesService seriesService){
        this.seriesService = seriesService;
    }
    @PreAuthorize("permitAll()")
    @GetMapping(SEARCH)
    public Stream<SeriesSearchDto> findByTitleAndGenreListNullSafe(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<String> genreList,
            @RequestParam(required = false) List<String> workerList) {
        return this.seriesService.findByTitleAndGenreListNullSafe(title,genreList, workerList);
    }
    @PreAuthorize("permitAll()")
    @PostMapping(produces = {"application/json"})
    public Optional<Series> create(@Valid @RequestBody SeriesFormDto seriesFormDto) {
        seriesFormDto.doDefault();
        return this.seriesService.create(seriesFormDto);
    }
    @PreAuthorize("permitAll()")
    @GetMapping(PICTURES+ID)
    public ResponseEntity<byte[]> getPicture(@PathVariable Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE));
        byte[] media = this.seriesService.findById(id).get().getPoster();
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;
    }
    @PreAuthorize("permitAll()")
    @GetMapping(ID)
    public Optional<SeriesReviewDto> read(@PathVariable Integer id) {
        return this.seriesService.read(id);
    }
}
