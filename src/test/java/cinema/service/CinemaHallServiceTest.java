package cinema.service;

import cinema.dao.CinemaHallDao;
import cinema.model.CinemaHall;
import cinema.service.impl.CinemaHallServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

class CinemaHallServiceTest {
    private static final int EXPECTED_ID = 1;
    private CinemaHall cinemaHallGreen;
    private CinemaHall expectedCinemaHall;
    private static CinemaHallService cinemaHallService;
    private static CinemaHallDao cinemaHallDao;

    @BeforeAll
    static void beforeAll() {
        cinemaHallDao = Mockito.mock(CinemaHallDao.class);
        cinemaHallService = new CinemaHallServiceImpl(cinemaHallDao);
    }

    @BeforeEach
    void setUp() {
        cinemaHallGreen = new CinemaHall();
        cinemaHallGreen.setCapacity(1);
        cinemaHallGreen.setDescription("Green");

        expectedCinemaHall = new CinemaHall();
        expectedCinemaHall.setCapacity(1);
        expectedCinemaHall.setDescription("Green");
        expectedCinemaHall.setId(1L);
    }

    @Test
    void add_Ok() {
        Mockito.when(cinemaHallDao.add(cinemaHallGreen)).thenReturn(expectedCinemaHall);
        CinemaHall addedCinemaHall = cinemaHallService.add(cinemaHallGreen);
        Assertions.assertNotNull(addedCinemaHall);
        Assertions.assertEquals(EXPECTED_ID, addedCinemaHall.getId());
    }

    @Test
    void getById_Ok() {
        Mockito.when(cinemaHallDao.get(1L)).thenReturn(Optional.ofNullable(expectedCinemaHall));
        CinemaHall cinemaHallFromDb = cinemaHallService.get(1L);
        Assertions.assertNotNull(cinemaHallFromDb);
    }

    @Test
    void getById_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> cinemaHallService.get(2L));
    }

    @Test
    void getAll_Ok() {
        List<CinemaHall> cinemaHalls = List.of(expectedCinemaHall);
        Mockito.when(cinemaHallDao.getAll()).thenReturn(cinemaHalls);
        List<CinemaHall> cinemaHallsFromDb = cinemaHallService.getAll();
        int expectedListSize = 1;
        Assertions.assertEquals(expectedListSize, cinemaHallsFromDb.size());
    }
}
