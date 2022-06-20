package es.tfm.fsa.infraestructure.api.resources;

import es.tfm.fsa.configuration.JwtService;
import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Rating;
import es.tfm.fsa.domain.services.RatingService;
import es.tfm.fsa.infraestructure.api.Rest;
import es.tfm.fsa.infraestructure.api.dtos.RatingFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Rest
@RequestMapping(RatingResource.RATINGS)
public class RatingResource {
    public static final String RATINGS = "/ratings";
    public static final String ID = "/{videoProductionId}";

    private RatingService ratingService;
    private JwtService jwtService;

    @Autowired
    public RatingResource(RatingService ratingService, JwtService jwtService){
        this.ratingService = ratingService;
        this.jwtService = jwtService;
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping(produces = {"application/json"})
    public Optional<Integer> create(@RequestHeader("Authorization") String token, @Valid @RequestBody RatingFormDto ratingFormDto) {
        String extractedToken = this.jwtService.extractBearerToken(token);
        ratingFormDto.setUsername(this.jwtService.user(extractedToken));
        return this.ratingService.create(ratingFormDto);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping(ID)
    public Optional<Integer> read(@RequestHeader("Authorization") String token, @PathVariable Integer videoProductionId) {
        String extractedToken = this.jwtService.extractBearerToken(token);
        return this.ratingService.read(this.jwtService.user(extractedToken), videoProductionId);
    }

}
