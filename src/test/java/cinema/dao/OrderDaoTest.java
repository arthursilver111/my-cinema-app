package cinema.dao;

import cinema.dao.impl.*;
import cinema.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;

class OrderDaoTest extends AbstractTest {
    private OrderDao orderDao;
    private Order order;
    private User userFromDb;

    @Override
    protected Class<?>[] entities() {
        return new Class[]{Order.class, Ticket.class, User.class,
                MovieSession.class, CinemaHall.class, Movie.class, Role.class};
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
        userFromDb = userDao.add(user);

        Ticket ticket = new Ticket();
        ticket.setMovieSession(addMovieSession);
        ticket.setUser(userFromDb);
        TicketDao ticketDao = new TicketDaoImpl(getSessionFactory());
        Ticket addTicket = ticketDao.add(ticket);

        order = new Order();
        order.setTickets(List.of(addTicket));
        order.setUser(userFromDb);
        orderDao = new OrderDaoImpl(getSessionFactory());
        orderDao.add(order);
    }

    @Test
    void getOrdersHistory_Ok() {
        List<Order> ordersHistory = orderDao.getOrdersHistory(userFromDb);
        int expectedSize = 1;
        Assertions.assertEquals(expectedSize, ordersHistory.size());
    }
}
