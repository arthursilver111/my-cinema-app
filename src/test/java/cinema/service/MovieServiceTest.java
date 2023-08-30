package cinema.service;

import cinema.dao.MovieDao;
import cinema.model.Movie;
import cinema.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

class MovieServiceTest {
    private static final int EXPECTED_ID = 1;
    private static MovieService movieService;
    private Movie movie;
    private static MovieDao movieDao;


    @BeforeAll
    static void beforeAll() {
        movieDao = Mockito.mock(MovieDao.class);
        movieService = new MovieServiceImpl(movieDao);
    }

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setTitle("Avatar");
        movie.setDescription("Good movie");
    }

    @Test
    void add_Ok() {
        Mockito.when(movieDao.add(movie)).thenReturn(movie);
        movie.setId(1L);
        Movie movieFromDb = movieService.add(movie);
        Assertions.assertNotNull(movieFromDb);
        Assertions.assertEquals(EXPECTED_ID, movieFromDb.getId());
    }

    @Test
    void getById_OK() {
        Mockito.when(movieDao.get(1L)).thenReturn(Optional.of(movie));
        Movie movieFromDb = movieService.get(1L);
        Assertions.assertNotNull(movieFromDb);
    }

    @Test
    void getById_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> movieService.get(2L));
    }

    @Test
    void getAll_Ok() {
        movie.setId(1L);
        List<Movie> movies = List.of(movie);
        Mockito.when(movieDao.getAll()).thenReturn(movies);
        List<Movie> movieFromDb = movieService.getAll();
        int expectedListSize = 1;
        Assertions.assertEquals(expectedListSize, movieFromDb.size());
    }
}
