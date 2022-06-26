package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.configuration.JwtService;
import es.tfm.fsa.domain.services.FilmService;
import es.tfm.fsa.domain.services.VideoProductionService;
import es.tfm.fsa.infraestructure.api.Rest;
import es.tfm.fsa.infraestructure.api.dtos.FilmSearchDto;
import es.tfm.fsa.infraestructure.api.dtos.VideoProductionMyListSearchDto;
import es.tfm.fsa.infraestructure.api.dtos.VideoProductionSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@Rest
@RequestMapping(VideoProductionResource.VIDEO_PRODUCTIONS)
public class VideoProductionResource {
    public static final String VIDEO_PRODUCTIONS = "/video-productions";
    public static final String SEARCH = "/search";
    public static final String PICTURES = "/pictures";
    public static final String ID = "/{id}";
    public static final String SEARCH_MY_LIST = "/search-my-list";

    private VideoProductionService videoProductionService;
    private JwtService jwtService;

    @Autowired
    public VideoProductionResource(VideoProductionService videoProductionService, JwtService jwtService){
        this.videoProductionService = videoProductionService;
        this.jwtService = jwtService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping(SEARCH)
    public Stream<VideoProductionSearchDto> findByTitleAndGenreListNullSafe(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<String> genreList) {
        return this.videoProductionService.findByTitleAndGenreListNullSafe(title, genreList);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping(SEARCH_MY_LIST)
    public Stream<VideoProductionMyListSearchDto> findByTitleAndGenreListNullSafe(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false) String title) {
        String extractedToken = this.jwtService.extractBearerToken(token);
        return this.videoProductionService.findByTitleAndUsernameList(title,this.jwtService.user(extractedToken));
    }
    @PreAuthorize("permitAll()")
    @GetMapping(PICTURES+ID)
    public ResponseEntity<byte[]> getPicture(@PathVariable Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE));
        byte[] media = this.videoProductionService.findById(id).get().getPoster();
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;
    }
}
