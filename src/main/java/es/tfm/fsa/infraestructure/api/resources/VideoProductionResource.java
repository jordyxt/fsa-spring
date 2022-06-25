package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.domain.services.FilmService;
import es.tfm.fsa.domain.services.VideoProductionService;
import es.tfm.fsa.infraestructure.api.Rest;
import es.tfm.fsa.infraestructure.api.dtos.FilmSearchDto;
import es.tfm.fsa.infraestructure.api.dtos.VideoProductionSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Stream;

@Rest
@RequestMapping(VideoProductionResource.VIDEO_PRODUCTIONS)
public class VideoProductionResource {
    public static final String VIDEO_PRODUCTIONS = "/video-productions";
    public static final String SEARCH = "/search";

    private VideoProductionService videoProductionService;
    @Autowired
    public VideoProductionResource(VideoProductionService videoProductionService){
        this.videoProductionService = videoProductionService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping(SEARCH)
    public Stream<VideoProductionSearchDto> findByTitleAndGenreListNullSafe(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<String> genreList) {
        return this.videoProductionService.findByTitleAndGenreListNullSafe(title, genreList);
    }
}
