package cinema.service.mapper;

import cinema.dto.request.MovieSessionRequestDto;
import cinema.dto.response.MovieSessionResponseDto;
import cinema.model.CinemaHall;
import cinema.model.Movie;
import cinema.model.MovieSession;
import cinema.service.CinemaHallService;
import cinema.service.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

class MovieSessionMapperTest {
    private static CinemaHallService cinemaHallService;
    private static MovieService movieService;
    private static MovieSessionMapper movieSessionMapper;
    private Movie movie;
    private CinemaHall cinemaHall;
    private final Long cinemaHallId = 1L;
    private final Long movieId = 1L;

    @BeforeAll
    static void beforeAll() {
        cinemaHallService = Mockito.mock(CinemaHallService.class);
        movieService = Mockito.mock(MovieService.class);
        movieSessionMapper = new MovieSessionMapper(cinemaHallService, movieService);
    }

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setTitle("Avatar");
        movie.setDescription("Good");
        movie.setId(movieId);

        cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription("Good hall");
        cinemaHall.setId(cinemaHallId);
    }

    @Test
    void mapToModel_Ok() {
        MovieSessionRequestDto movieSessionRequestDto = new MovieSessionRequestDto();
        Class<?> dtoClass = movieSessionRequestDto.getClass();
        try {
            Field fieldCinemaHallId = dtoClass.getDeclaredField("cinemaHallId");
            fieldCinemaHallId.setAccessible(true);
            fieldCinemaHallId.set(movieSessionRequestDto, cinemaHallId);

            Field fieldMovieId = dtoClass.getDeclaredField("movieId");
            fieldMovieId.setAccessible(true);
            fieldMovieId.set(movieSessionRequestDto, movieId);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Could not find field", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not set data to field", e);
        }
        Mockito.when(movieService.get(movieId)).thenReturn(movie);
        Mockito.when(cinemaHallService.get(cinemaHallId)).thenReturn(cinemaHall);
        MovieSession movieSession = movieSessionMapper.mapToModel(movieSessionRequestDto);
        Assertions.assertNotNull(movieSession);
        Assertions.assertEquals(movie.getId(), movieSession.getMovie().getId());
        Assertions.assertEquals(cinemaHall.getId(), movieSession.getCinemaHall().getId());
    }

    @Test
    void mapToDto_Ok() {
        MovieSession movieSession = new MovieSession();
        movieSession.setId(1L);
        movieSession.setShowTime(LocalDateTime.now());
        movieSession.setMovie(movie);
        movieSession.setCinemaHall(cinemaHall);

        MovieSessionResponseDto movieSessionResponseDto = movieSessionMapper.mapToDto(movieSession);
        Assertions.assertNotNull(movieSessionResponseDto);
        Assertions.assertEquals(movieSession.getMovie().getId(), movieSessionResponseDto.getMovieId());
        Assertions.assertEquals(movieSession.getCinemaHall().getId(), movieSessionResponseDto.getCinemaHallId());
    }
}
