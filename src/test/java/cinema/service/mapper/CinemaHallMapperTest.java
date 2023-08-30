package cinema.service.mapper;

import cinema.dto.request.CinemaHallRequestDto;
import cinema.dto.response.CinemaHallResponseDto;
import cinema.model.CinemaHall;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

class CinemaHallMapperTest {
    private static CinemaHallMapper cinemaHallMapper;
    private final int capacity = 100;
    private final String description = "Good";

    @BeforeAll
    static void beforeAll() {
        cinemaHallMapper = new CinemaHallMapper();
    }

    @Test
    void mapToModel_Ok() {
        CinemaHallRequestDto cinemaHallRequestDto = new CinemaHallRequestDto();
        Class<?> dtoClass = cinemaHallRequestDto.getClass();
        try {
            Field fieldCapacity = dtoClass.getDeclaredField("capacity");
            fieldCapacity.setAccessible(true);
            fieldCapacity.set(cinemaHallRequestDto, capacity);

            Field fieldDescription = dtoClass.getDeclaredField("description");
            fieldDescription.setAccessible(true);
            fieldDescription.set(cinemaHallRequestDto, description);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Could not find field", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not set data to field", e);
        }
        CinemaHall cinemaHall = cinemaHallMapper.mapToModel(cinemaHallRequestDto);
        Assertions.assertEquals(capacity, cinemaHall.getCapacity());
        Assertions.assertEquals(description, cinemaHall.getDescription());
    }

    @Test
    void mapToDto_Ok() {
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(capacity);
        cinemaHall.setDescription(description);
        CinemaHallResponseDto cinemaHallResponseDto = cinemaHallMapper.mapToDto(cinemaHall);
        Assertions.assertNotNull(cinemaHallResponseDto);
        Assertions.assertEquals(cinemaHall.getCapacity(), cinemaHallResponseDto.getCapacity());
        Assertions.assertEquals(cinemaHall.getDescription(), cinemaHallResponseDto.getDescription());
    }
}
