package es.tfm.fsa.infraestructure.postgres.daos;

import es.tfm.fsa.domain.model.Role;
import es.tfm.fsa.domain.model.VideoProductionWorkerRole;
import es.tfm.fsa.infraestructure.postgres.daos.synchronous.*;
import es.tfm.fsa.infraestructure.postgres.entities.*;
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
import java.util.Collections;
import java.util.List;

@Service // @Profile("dev")
public class DatabaseSeederDev {

    private DatabaseStarting databaseStarting;
    private UserDao userDao;
    private GenreDao genreDao;
    private RatingDao ratingDao;
    private FilmDao filmDao;
    private SeriesDao seriesDao;
    private VideoProductionWorkerDao videoProductionWorkerDao;
    private TopicDao topicDao;
    private MessageDao messageDao;
    @Autowired
    public DatabaseSeederDev(UserDao userDao, GenreDao genreDao, RatingDao ratingDao, FilmDao filmDao,
                             SeriesDao seriesDao, VideoProductionWorkerDao videoProductionWorkerDao,
                             TopicDao topicDao, MessageDao messageDao,
                             DatabaseStarting databaseStarting) {
        this.userDao = userDao;
        this.genreDao = genreDao;
        this.filmDao = filmDao;
        this.seriesDao = seriesDao;
        this.ratingDao = ratingDao;
        this.videoProductionWorkerDao = videoProductionWorkerDao;
        this.topicDao = topicDao;
        this.messageDao = messageDao;
        this.databaseStarting = databaseStarting;
        this.deleteAllAndInitializeAndSeedDataBase();
    }
    public void deleteAllAndInitializeAndSeedDataBase() {
        this.deleteAllAndInitialize();
        this.seedDataBase();
    }

    public void deleteAllAndInitialize() {
        this.userDao.deleteAll();
        this.ratingDao.deleteAll();
        this.filmDao.deleteAll();
        this.seriesDao.deleteAll();
        this.genreDao.deleteAll();
        this.videoProductionWorkerDao.deleteAll();
        this.topicDao.deleteAll();
        this.messageDao.deleteAll();
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
                GenreEntity.builder().name("fantasy").description("Fantasy").build(),
                GenreEntity.builder().name("crime").description("Crime").build(),
                GenreEntity.builder().name("animation").description("Animation").build()
        };
        this.genreDao.saveAll(Arrays.asList(genres));
        LogManager.getLogger(this.getClass()).warn("        ------- genres");
        VideoProductionWorkerEntity[] videoProductionWorkers = new VideoProductionWorkerEntity[]{
                VideoProductionWorkerEntity.builder().name("Colin Trevorrow").
                        description("Born in San Francisco and raised in Oakland.").
                        birthdate(LocalDate.of(1976, Month.SEPTEMBER, 13)).
                        videoProductionWorkerRoleList(Arrays.asList(
                                VideoProductionWorkerRole.DIRECTOR,
                                VideoProductionWorkerRole.PRODUCER,
                                VideoProductionWorkerRole.WRITER)).build(),
                VideoProductionWorkerEntity.builder().name("Chris Pratt").
                        description("Christopher Michael \"Chris\" Pratt was born on June 21, 1979 in Virginia," +
                                " Minnesota and raised in Lake Stevens, Washington.").
                        birthdate(LocalDate.of(1979, Month.JUNE, 21)).
                        videoProductionWorkerRoleList(Arrays.asList(
                                VideoProductionWorkerRole.ACTOR,
                                VideoProductionWorkerRole.PRODUCER)).build()
        };
        this.videoProductionWorkerDao.saveAll(Arrays.asList(videoProductionWorkers));
        LogManager.getLogger(this.getClass()).warn("        ------- videoProductionWorkers");
        FilmEntity[] films ={};
        try {
            films = new FilmEntity[]{
                    FilmEntity.BBuilder().title("Jurassic World Dominion")
                            .description("Four years after the destruction of Isla Nublar, " +
                                    "dinosaurs now live--and hunt--alongside humans all over the world.").
                            releaseDate(LocalDate.of(2022, Month.JUNE, 10)).
                            trailer("https://www.youtube.com/embed/fb5ELWi-ekk").
                            poster(downloadFile(
                                    new URL("https://www.universalpictures.es/tl_files/content/movies/" +
                                            "jurassic_world_dominion/poster/01.jpg"))).
                            genreEntityList(Arrays.asList(genres[1], genres[2], genres[3])).
                            directorEntityList(Collections.singletonList(videoProductionWorkers[0])).
                            actorEntityList(Collections.singletonList(videoProductionWorkers[1])).
                            build(),
                    FilmEntity.BBuilder().title("Fantastic Beasts: The Secrets of Dumbledore")
                            .description("Albus Dumbledore assigns Newt and his allies with a mission related to" +
                                    " the rising power of Grindelwald.").
                            releaseDate(LocalDate.of(2022, Month.APRIL, 17)).
                            poster(downloadFile(
                                    new URL("https://basededatos.atrae.org/media/works/" +
                                            "jrgifaYeUtTnaH7NF5Drkgjg2MB.jpg"))).
                            genreEntityList(Arrays.asList(genres[1], genres[2], genres[7]))
                            .build(),
                    FilmEntity.BBuilder().title("The Lost City")
                            .description("A reclusive romance novelist on a book tour with her cover model gets" +
                                    " swept up in a kidnapping attempt that lands them both in a cutthroat" +
                                    " jungle adventure.").
                            releaseDate(LocalDate.of(2022, Month.MARCH, 25)).
                            poster(downloadFile(
                                    new URL("https://pics.filmaffinity.com/" +
                                            "La_ciudad_perdida-310825716-large.jpg"))).
                            genreEntityList(Arrays.asList(genres[1], genres[2], genres[6]))
                            .build()
            };
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.filmDao.saveAll(Arrays.asList(films));
        LogManager.getLogger(this.getClass()).warn("        ------- films");
        RatingEntity[] rates = {
                RatingEntity.builder().rating(8).userEntity(users[0]).videoProductionEntity(films[0]).build(),
                RatingEntity.builder().rating(7).userEntity(users[1]).videoProductionEntity(films[0]).build(),

        };
        this.ratingDao.saveAll(Arrays.asList(rates));
        LogManager.getLogger(this.getClass()).warn("        ------- ratings");
        SeriesEntity[] series ={};
        try {
            series = new SeriesEntity[]{
                    SeriesEntity.BBuilder().title("How I Met Your Mother")
                            .description("A father recounts to his children - through a series of flashbacks - the "+
                                    "journey he and his four best friends took leading up to him meeting their mother.").
                            releaseDate(LocalDate.of(2005, Month.SEPTEMBER, 19)).
                            seasons(9).
                            endingDate(LocalDate.of(2014, Month.MARCH, 31)).
                            poster(downloadFile(
                                    new URL("https://m.media-amazon.com/images/M/"+
                                            "MV5BNjg1MDQ5MjQ2N15BMl5BanBnXkFtZTYwNjI5NjA3._V1_FMjpg_UX1000_.jpg"))).
                            genreEntityList(Arrays.asList(genres[6], genres[5]))
                            .build(),
                    SeriesEntity.BBuilder().title("Suits")
                            .description("On the run from a drug deal gone bad, brilliant college dropout Mike Ross "+
                                    "finds himself working with Harvey Specter, one of New York City's best lawyers.").
                            releaseDate(LocalDate.of(2011, Month.JUNE, 23)).
                            seasons(9).
                            endingDate(LocalDate.of(2019, Month.SEPTEMBER, 25)).
                            poster(downloadFile(
                                    new URL("https://es.web.img2.acsta.net/pictures/14/03/28/10/18/433386.jpg"))).
                            genreEntityList(Arrays.asList(genres[6], genres[4]))
                            .build(),
                    SeriesEntity.BBuilder().title("Money Heist")
                            .description("An unusual group of robbers attempt to carry out the most perfect robbery "+
                                    "in Spanish history - stealing 2.4 billion euros from the Royal Mint of Spain.").
                            releaseDate(LocalDate.of(2017, Month.MAY, 2)).
                            seasons(3).
                            endingDate(LocalDate.of(2021, Month.DECEMBER, 3)).
                            poster(downloadFile(
                                    new URL("https://static.wikia.nocookie.net/netflix/images/0/0e/"+
                                            "MH_S5_Promotional.jpg/revision/latest?cb=20210904021400"))).
                            genreEntityList(Arrays.asList(genres[1], genres[8], genres[4]))
                            .build()
            };
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.seriesDao.saveAll(Arrays.asList(series));
        LogManager.getLogger(this.getClass()).warn("        ------- series");
        TopicEntity[] topics = {
                TopicEntity.builder().title("Topic Example 1").description("This is an example.").
                        userEntity(users[1]).videoProductionEntity(films[0]).creationDate(LocalDateTime.now()).build(),
                TopicEntity.builder().title("Topic Example 2").description("This is an example.").
                        userEntity(users[1]).videoProductionEntity(films[1]).creationDate(LocalDateTime.now()).build(),
                TopicEntity.builder().title("Topic Example 3").description("This is an example.").
                        userEntity(users[1]).videoProductionEntity(films[2]).creationDate(LocalDateTime.now()).build()
        };
        this.topicDao.saveAll(Arrays.asList(topics));
        LogManager.getLogger(this.getClass()).warn("        ------- topics");
        MessageEntity[] messages = {
                MessageEntity.builder().message("This is an example 1.").
                        userEntity(users[1]).topicEntity(topics[0]).creationDate(LocalDateTime.now()).build(),
                MessageEntity.builder().message("This is an example 2.").
                        userEntity(users[1]).topicEntity(topics[0]).creationDate(LocalDateTime.now()).build(),
                MessageEntity.builder().message("This is an example 3.").
                        userEntity(users[1]).topicEntity(topics[0]).creationDate(LocalDateTime.now()).build()
        };
        this.messageDao.saveAll(Arrays.asList(messages));
        LogManager.getLogger(this.getClass()).warn("        ------- messages");
    }
    private byte[] downloadFile(URL url) {
        try {
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(conn.getInputStream(), baos);

            return  ArrayUtils.toPrimitive(ArrayUtils.toObject(baos.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
