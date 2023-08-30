package cinema.dao;

import cinema.dao.impl.MovieDaoImpl;
import cinema.model.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.NoSuchElementException;

class MovieDaoTest extends AbstractTest {
    private Movie movieAvatar;
    private MovieDao movieDao;

    @Override
    protected Class<?>[] entities() {
        return new Class[]{Movie.class};
    }

    @BeforeEach
    void setUp() {
        movieAvatar = new Movie();
        movieAvatar.setTitle("Avatar");
        movieAvatar.setDescription("Good film");
        movieDao = new MovieDaoImpl(getSessionFactory());
    }

    @Test
    void addMovie_Ok() {
        Movie savedMovie = movieDao.add(movieAvatar);
        Assertions.assertNotNull(savedMovie);
        int expectedId = 1;
        Assertions.assertEquals(expectedId, savedMovie.getId());
    }

    @Test
    void getMovie_Ok() {
        Movie savedMovie = movieDao.add(movieAvatar);
        Assertions.assertNotNull(savedMovie);
        Movie movieFromDb = movieDao
                .get(1L).orElseThrow(() -> new NoSuchElementException("Could not get element from DB"));
        int expectedId = 1;
        Assertions.assertEquals(expectedId, movieFromDb.getId());
    }

    @Test
    void getMovieAll_Ok() {
        movieDao.add(movieAvatar);
        Movie movieBatman = new Movie();
        movieAvatar.setTitle("Batman");
        movieAvatar.setDescription("Very good film");
        movieDao.add(movieBatman);
        List<Movie> allMovies = movieDao.getAll();
        int expectedSize = 2;
        Assertions.assertEquals(expectedSize, allMovies.size());
    }
}
