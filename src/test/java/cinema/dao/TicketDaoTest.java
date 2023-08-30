package cinema.dao;

import cinema.dao.impl.*;
import cinema.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;

class TicketDaoTest extends AbstractTest {
    private static TicketDao ticketDao;
    private static Ticket ticket;

    @Override
    protected Class<?>[] entities() {
        return new Class[]{MovieSession.class, Movie.class,
                CinemaHall.class, User.class, Role.class, Ticket.class};
    }

    @BeforeEach
    void setUp() {
        MovieDao movieDao = new MovieDaoImpl(getSessionFactory());
        Movie addedMovie = movieDao.add(new Movie());

        CinemaHallDao cinemaHallDao = new CinemaHallDaoImpl(getSessionFactory());
        CinemaHall addedCinemaHall = cinemaHallDao.add(new CinemaHall());

        MovieSessionDao movieSessionDao = new MovieSessionDaoImpl(getSessionFactory());
        MovieSession addMovieSession = movieSessionDao.add(new MovieSession(addedMovie, addedCinemaHall));

        Role role = new Role();
        role.setRoleName(Role.RoleName.USER);
        RoleDao roleDao = new RoleDaoImpl(getSessionFactory());
        Role addedRole = roleDao.add(role);

        User user = new User();
        user.setRoles(Set.of(addedRole));
        UserDao userDao = new UserDaoImpl(getSessionFactory());
        user = userDao.add(user);

        ticket = new Ticket();
        ticket.setMovieSession(addMovieSession);
        ticket.setUser(user);
        ticketDao = new TicketDaoImpl(getSessionFactory());
    }

    @Test
    void add_Ok() {
        Ticket savedTicket = ticketDao.add(ticket);
        Assertions.assertNotNull(savedTicket);
    }
}
