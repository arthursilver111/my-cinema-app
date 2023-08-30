package cinema.dao;

import cinema.dao.impl.CinemaHallDaoImpl;
import cinema.dao.impl.MovieDaoImpl;
import cinema.dao.impl.MovieSessionDaoImpl;
import cinema.model.CinemaHall;
import cinema.model.Movie;
import cinema.model.MovieSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

class MovieSessionDaoTest extends AbstractTest {
    private MovieDao movieDao;
    private MovieSessionDao movieSessionDao;
    private MovieSession movieSession;

    @Override
    protected Class<?>[] entities() {
        return new Class[]{MovieSession.class, Movie.class, CinemaHall.class};
    }

    @BeforeEach
    void setUp() {
        movieDao = new MovieDaoImpl(getSessionFactory());
        movieSessionDao = new MovieSessionDaoImpl(getSessionFactory());
        CinemaHallDao cinemaHallDao = new CinemaHallDaoImpl(getSessionFactory());

        CinemaHall cinemaHallGreen = new CinemaHall();
        cinemaHallGreen.setCapacity(100);
        cinemaHallGreen.setDescription("Green hall");
        CinemaHall savedCinemaHallGreen = cinemaHallDao.add(cinemaHallGreen);

        Movie movieAvatar = new Movie();
        movieAvatar.setTitle("Avatar");
        movieAvatar.setDescription("Good film");
        Movie savedMovieAvatar = movieDao.add(movieAvatar);
        movieAvatar.setId(savedMovieAvatar.getId());

        movieSession = new MovieSession();
        movieSession.setMovie(savedMovieAvatar);
        movieSession.setCinemaHall(savedCinemaHallGreen);
        movieSession.setShowTime(LocalDateTime.now());
        movieSessionDao.add(movieSession);
    }

    @Test
    void addMovieSession_Ok() {
        MovieSession movieSessionFromDb = movieSessionDao.get(1L)
                .orElseThrow(() -> new NoSuchElementException("Could not get element from DB"));
        int expectedId = 1;
        Assertions.assertEquals(expectedId, movieSessionFromDb.getId());
    }

    @Test
    void deleteMovieSession_Ok() {
        movieSessionDao.delete(1L);
        Optional<MovieSession> emptyMovieSession = movieSessionDao.get(1L);
        Assertions.assertTrue(emptyMovieSession.isEmpty());
    }

    @Test
    void updateMovieSessions_Ok() {
        Movie movieBatman = new Movie();
        movieBatman.setTitle("Batman");
        movieBatman.setDescription("Good film");
        Movie addedMovie = movieDao.add(movieBatman);
        movieSession.setMovie(addedMovie);
        MovieSession updatedMovieSession = movieSessionDao.update(movieSession);
        Assertions.assertEquals(movieSession, updatedMovieSession);
    }
}
