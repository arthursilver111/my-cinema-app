package cinema.controller;

import cinema.dto.request.CinemaHallRequestDto;
import cinema.dto.response.CinemaHallResponseDto;
import cinema.model.CinemaHall;
import cinema.service.CinemaHallService;
import cinema.service.mapper.RequestDtoMapper;
import cinema.service.mapper.ResponseDtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.lang.reflect.Field;
import java.util.List;

class CinemaHallControllerTest {
    private static CinemaHallController cinemaHallController;
    private static CinemaHallService cinemaHallService;
    private static RequestDtoMapper<CinemaHallRequestDto, CinemaHall> cinemaHallRequestDtoMapper;
    private static ResponseDtoMapper<CinemaHallResponseDto, CinemaHall> cinemaHallResponseDtoMapper;
    private CinemaHallRequestDto requestDto;
    private CinemaHall cinemaHall;

    @BeforeAll
    static void beforeAll() {
        cinemaHallService = Mockito.mock(CinemaHallService.class);
        cinemaHallRequestDtoMapper = Mockito.mock(RequestDtoMapper.class);
        cinemaHallResponseDtoMapper = Mockito.mock(ResponseDtoMapper.class);
        cinemaHallController = new CinemaHallController(cinemaHallService,
                cinemaHallRequestDtoMapper, cinemaHallResponseDtoMapper);
    }

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        requestDto = new CinemaHallRequestDto();

        Class<?> requestDtoClass = requestDto.getClass();
        Field fieldCapacity = requestDtoClass.getDeclaredField("capacity");
        fieldCapacity.setAccessible(true);
        fieldCapacity.set(requestDto, 10);

        Field fieldDescription = requestDtoClass.getDeclaredField("description");
        fieldDescription.setAccessible(true);
        fieldDescription.set(requestDto, "Good");

        cinemaHall = new CinemaHall();
        cinemaHall.setId(1L);
        cinemaHall.setDescription(requestDto.getDescription());
        cinemaHall.setCapacity(requestDto.getCapacity());

        CinemaHallResponseDto cinemaHallResponseDto = new CinemaHallResponseDto();
        cinemaHallResponseDto.setCapacity(cinemaHall.getCapacity());
        cinemaHallResponseDto.setId(cinemaHall.getId());
        cinemaHallResponseDto.setDescription(cinemaHall.getDescription());

        Mockito.when(cinemaHallRequestDtoMapper.mapToModel(requestDto)).thenReturn(cinemaHall);
        Mockito.when(cinemaHallResponseDtoMapper.mapToDto(cinemaHall)).thenReturn(cinemaHallResponseDto);
    }

    @Test
    void add_Ok() {
        Mockito.when(cinemaHallService.add(cinemaHall)).thenReturn(cinemaHall);

        CinemaHallResponseDto addedCinemaHallResponseDto = cinemaHallController.add(requestDto);
        Assertions.assertEquals(requestDto.getCapacity(), addedCinemaHallResponseDto.getCapacity());
        Assertions.assertEquals(requestDto.getDescription(), addedCinemaHallResponseDto.getDescription());
    }

    @Test
    void getAll_Ok() {
        List<CinemaHall> cinemaHallList = List.of(cinemaHall);
        Mockito.when(cinemaHallService.getAll()).thenReturn(cinemaHallList);
        List<CinemaHallResponseDto> allCinemaHallResponseDtos = cinemaHallController.getAll();
        Assertions.assertNotNull(allCinemaHallResponseDtos);
    }
}
