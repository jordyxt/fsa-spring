package es.tfm.fsa.infraestructure.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import es.tfm.fsa.domain.model.Film;
import es.tfm.fsa.domain.model.Genre;
import es.tfm.fsa.domain.model.User;
import es.tfm.fsa.domain.model.VideoProductionType;
import es.tfm.fsa.infraestructure.postgres.entities.GenreEntity;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@ToString(callSuper=true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilmFormDto extends VideoProductionFormDto {
    @Builder(builderMethodName = "BBuilder")
    public FilmFormDto(String title, String description,
                         LocalDate releaseDate, List<String> genreList,
                         String poster,String trailer, List<String> directorList, List<String> actorList) {
        super(title, description, releaseDate, genreList, poster, trailer,
                directorList, actorList, VideoProductionType.FILM);
    }
}
