package cinema.service;

import cinema.dao.MovieSessionDao;
import cinema.model.CinemaHall;
import cinema.model.Movie;
import cinema.model.MovieSession;
import cinema.service.impl.MovieSessionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

class MovieSessionServiceTest {
    private static MovieSessionService movieSessionService;
    private MovieSession movieSession;
    private static MovieSessionDao movieSessionDao;

    @BeforeAll
    static void beforeAll() {
        movieSessionDao = Mockito.mock(MovieSessionDao.class);
        movieSessionService = new MovieSessionServiceImpl(movieSessionDao);
    }

    @BeforeEach
    void setUp() {
        CinemaHall cinemaHallGreen = new CinemaHall();
        cinemaHallGreen.setId(1L);
        cinemaHallGreen.setCapacity(100);
        cinemaHallGreen.setDescription("Green hall");

        Movie movieAvatar = new Movie();
        movieAvatar.setId(1L);
        movieAvatar.setTitle("Avatar");
        movieAvatar.setDescription("Good film");

        movieSession = new MovieSession();
        movieSession.setMovie(movieAvatar);
        movieSession.setCinemaHall(cinemaHallGreen);
        movieSession.setShowTime(LocalDateTime.now());
    }

    @Test
    void findAvailableSessions_Ok() {
        movieSession.setId(1L);
        List<MovieSession> expectedAvailableSessions = List.of(movieSession);
        Mockito.when(movieSessionDao.findAvailableSessions(1L, LocalDate.now())).thenReturn(expectedAvailableSessions);
        List<MovieSession> availableSessionsFromDb = movieSessionService.findAvailableSessions(1L, LocalDate.now());
        int expectedSize = 1;
        Assertions.assertNotNull(availableSessionsFromDb);
        Assertions.assertEquals(expectedSize, availableSessionsFromDb.size());
    }

    @Test
    void add_Ok() {
        Mockito.when(movieSessionDao.add(movieSession)).thenReturn(movieSession);
        MovieSession addedMovieSession = movieSessionService.add(movieSession);
        Assertions.assertNotNull(addedMovieSession);
    }

    @Test
    void getById_Ok() {
        Mockito.when(movieSessionDao.get(1L)).thenReturn(Optional.of(movieSession));
        MovieSession movieSessionFromDb = movieSessionService.get(1L);
        Assertions.assertNotNull(movieSessionFromDb);
    }

    @Test
    void getById_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> movieSessionService.get(2L));
    }

    @Test
    void update_Ok() {
        Movie movieBatman = new Movie();
        movieBatman.setId(2L);
        movieBatman.setTitle("Batman");
        movieBatman.setDescription("Good movie");
        movieSession.setMovie(movieBatman);

        Mockito.when(movieSessionDao.update(movieSession)).thenReturn(movieSession);
        MovieSession updatedMovieSession = movieSessionService.update(movieSession);
        Assertions.assertNotNull(updatedMovieSession);
        Assertions.assertEquals(movieBatman, updatedMovieSession.getMovie());
    }
}
