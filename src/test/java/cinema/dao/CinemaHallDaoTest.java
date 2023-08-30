package cinema.dao;

import cinema.dao.impl.CinemaHallDaoImpl;
import cinema.exception.DataProcessingException;
import cinema.model.CinemaHall;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.NoSuchElementException;

class CinemaHallDaoTest extends AbstractTest {
    private CinemaHallDao cinemaHallDao;
    private CinemaHall cinemaHall;

    @Override
    protected Class<?>[] entities() {
        return new Class[]{CinemaHall.class};
    }

    @BeforeEach
    void setUp() {
        cinemaHallDao = new CinemaHallDaoImpl(getSessionFactory());
        cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(10);
        cinemaHall.setDescription("Best hall");
    }

    @Test
    void addCinemaHall_Ok() {
        CinemaHall savedCinemaHall = cinemaHallDao.add(cinemaHall);
        Assertions.assertNotNull(savedCinemaHall);
        int expectedId = 1;
        Assertions.assertEquals(expectedId, savedCinemaHall.getId());
    }

    @Test
    void getCinema_Ok() {
        CinemaHall savedCinemaHall = cinemaHallDao.add(cinemaHall);
        Assertions.assertNotNull(savedCinemaHall);
        CinemaHall cinemaHallFromDb = cinemaHallDao
                .get(1L).orElseThrow(() -> new NoSuchElementException("Could not get element from DB"));
        int expectedId = 1;
        Assertions.assertEquals(expectedId, cinemaHallFromDb.getId());
    }

    @Test
    void getAllCinemaHall_Ok() {
        cinemaHallDao.add(cinemaHall);
        CinemaHall cinemaHallBlue = new CinemaHall();
        cinemaHallBlue.setDescription("Blue hall");
        cinemaHallBlue.setCapacity(200);
        cinemaHallDao.add(cinemaHallBlue);
        List<CinemaHall> allCinemaHalls = cinemaHallDao.getAll();
        int expectedSize = 2;
        Assertions.assertEquals(expectedSize, allCinemaHalls.size());
    }

    @Test
    void add_NotOk() {
        Assertions.assertThrows(DataProcessingException.class, () -> cinemaHallDao.add(null));
    }
}
