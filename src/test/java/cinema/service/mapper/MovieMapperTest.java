package cinema.service.mapper;

import cinema.dto.request.MovieRequestDto;
import cinema.dto.response.MovieResponseDto;
import cinema.model.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

class MovieMapperTest {
    private static MovieMapper movieMapperMapper;
    private final String title = "Good film";
    private final String description = "Avatar";

    @BeforeAll
    static void beforeAll() {
        movieMapperMapper = new MovieMapper();
    }

    @Test
    void mapToModel_Ok() {
        MovieRequestDto movieRequestDto = new MovieRequestDto();
        Class<?> dtoClass = movieRequestDto.getClass();
        try {
            Field fieldDescription = dtoClass.getDeclaredField("description");
            fieldDescription.setAccessible(true);
            fieldDescription.set(movieRequestDto, description);

            Field fieldTitle = dtoClass.getDeclaredField("title");
            fieldTitle.setAccessible(true);
            fieldTitle.set(movieRequestDto, title);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Could not find field", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not set data to field", e);
        }
        Movie movie = movieMapperMapper.mapToModel(movieRequestDto);
        Assertions.assertEquals(title, movie.getTitle());
        Assertions.assertEquals(description, movie.getDescription());
    }

    @Test
    void mapToDto_Ok() {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDescription(description);
        MovieResponseDto movieResponseDto = movieMapperMapper.mapToDto(movie);
        Assertions.assertNotNull(movieResponseDto);
        Assertions.assertEquals(movie.getTitle(), movieResponseDto.getTitle());
        Assertions.assertEquals(movie.getDescription(), movieResponseDto.getDescription());
    }
}
