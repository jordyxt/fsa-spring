package es.tfm.fsa.infraestructure.postgres.daos;

import es.tfm.fsa.domain.model.Role;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.FilmDao;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.GenreDao;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.UserDao;
import es.tfm.fsa.infraestructure.postgres.entities.FilmEntity;
import es.tfm.fsa.infraestructure.postgres.entities.GenreEntity;
import es.tfm.fsa.infraestructure.postgres.entities.UserEntity;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service // @Profile("dev")
public class DatabaseSeederDev {

    private DatabaseStarting databaseStarting;
    private UserDao userDao;
    private GenreDao genreDao;
    private FilmDao filmDao;
    @Autowired
    public DatabaseSeederDev(UserDao userDao, GenreDao genreDao, FilmDao filmDao, DatabaseStarting databaseStarting) {
        this.userDao = userDao;
        this.genreDao = genreDao;
        this.filmDao = filmDao;
        this.databaseStarting = databaseStarting;
        this.deleteAllAndInitializeAndSeedDataBase();
    }
    public void deleteAllAndInitializeAndSeedDataBase() {
        this.deleteAllAndInitialize();
        this.seedDataBase();
    }

    public void deleteAllAndInitialize() {
        this.userDao.deleteAll();
        this.filmDao.deleteAll();
        this.genreDao.deleteAll();
        LogManager.getLogger(this.getClass()).warn("------- Deleted All -----------");
        this.databaseStarting.initialize();
    }

    private void seedDataBase() {
        LogManager.getLogger(this.getClass()).warn("------- Initial Load from JAVA -----------");
        String pass = new BCryptPasswordEncoder().encode("12345");
        UserEntity[] users = {
                UserEntity.builder().username("adm1").password(pass).email("adm1@gmail.com")
                        .role(Role.ADMIN).registrationDate(LocalDateTime.now()).active(true)
                        .build(),
                UserEntity.builder().username("user1").password(pass).email("user1@gmail.com")
                        .role(Role.BASIC).registrationDate(LocalDateTime.now()).active(true)
                        .build()
        };
        this.userDao.saveAll(List.of(users));
        LogManager.getLogger(this.getClass()).warn("        ------- users");
        GenreEntity[] genres = {
                GenreEntity.builder().name("new-release").description("New release").build(),
                GenreEntity.builder().name("action").description("Action").build(),
                GenreEntity.builder().name("adventure").description("Adventure").build(),
                GenreEntity.builder().name("sci-fi").description("Sci-Fi").build(),
                GenreEntity.builder().name("drama").description("Drama").build(),
                GenreEntity.builder().name("romance").description("Romance").build(),
                GenreEntity.builder().name("comedy").description("Comedy").build(),
                GenreEntity.builder().name("fantasy").description("Fantasy").build()
        };
        this.genreDao.saveAll(Arrays.asList(genres));
        LogManager.getLogger(this.getClass()).warn("        ------- genres");
        FilmEntity[] films ={};
        try {
            films = new FilmEntity[]{
                    FilmEntity.builder().title("Jurassic World Dominion")
                            .description("Four years after the destruction of Isla Nublar, " +
                                    "dinosaurs now live--and hunt--alongside humans all over the world.").
                            releaseDate(LocalDate.of(2022, Month.JUNE, 10)).
                            poster(downloadFile(
                                    new URL("https://www.universalpictures.es/tl_files/content/movies/" +
                                            "jurassic_world_dominion/poster/01.jpg"))).
                            genreEntityList(Arrays.asList(new GenreEntity[]{genres[1], genres[2], genres[3]}))
                            .build(),
                    FilmEntity.builder().title("Fantastic Beasts: The Secrets of Dumbledore")
                            .description("Albus Dumbledore assigns Newt and his allies with a mission related to" +
                                    " the rising power of Grindelwald.").
                            releaseDate(LocalDate.of(2022, Month.APRIL, 17)).
                            poster(downloadFile(
                                    new URL("https://basededatos.atrae.org/media/works/" +
                                            "jrgifaYeUtTnaH7NF5Drkgjg2MB.jpg"))).
                            genreEntityList(Arrays.asList(new GenreEntity[]{genres[1], genres[2], genres[7]}))
                            .build(),
                    FilmEntity.builder().title("The Lost City")
                            .description("A reclusive romance novelist on a book tour with her cover model gets" +
                                    " swept up in a kidnapping attempt that lands them both in a cutthroat" +
                                    " jungle adventure.").
                            releaseDate(LocalDate.of(2022, Month.MARCH, 25)).
                            poster(downloadFile(
                                    new URL("https://pics.filmaffinity.com/" +
                                            "La_ciudad_perdida-310825716-large.jpg"))).
                            genreEntityList(Arrays.asList(new GenreEntity[]{genres[1], genres[2], genres[6]}))
                            .build()
            };
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.filmDao.saveAll(Arrays.asList(films));
        LogManager.getLogger(this.getClass()).warn("        ------- films");
    }
    private Byte[] downloadFile(URL url) {
        try {
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(conn.getInputStream(), baos);

            return ArrayUtils.toObject(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
