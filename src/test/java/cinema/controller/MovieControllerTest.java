package cinema.controller;

import cinema.dto.request.MovieRequestDto;
import cinema.dto.response.MovieResponseDto;
import cinema.model.Movie;
import cinema.service.MovieService;
import cinema.service.mapper.RequestDtoMapper;
import cinema.service.mapper.ResponseDtoMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MovieControllerTest {
    private static MovieController movieController;
    private static MovieService movieService;
    private static RequestDtoMapper<MovieRequestDto, Movie> movieRequestDtoMapper;
    private static ResponseDtoMapper<MovieResponseDto, Movie> movieResponseDtoMapper;
    private MovieRequestDto movieRequestDto;

    @BeforeAll
    static void beforeAll() {
        movieService = Mockito.mock(MovieService.class);
        movieRequestDtoMapper = Mockito.mock(RequestDtoMapper.class);
        movieResponseDtoMapper = Mockito.mock(ResponseDtoMapper.class);
        movieController = new MovieController(movieService, movieRequestDtoMapper,
                movieResponseDtoMapper);
    }

    @BeforeEach
    void setUp() {
        movieRequestDto = new MovieRequestDto();
    }

    @Test
    void add_OK() {
        movieController.add();
    }
}
