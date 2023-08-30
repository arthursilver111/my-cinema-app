package cinema.dao;

import cinema.dao.impl.*;
import cinema.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;


class ShoppingCartDaoTest extends AbstractTest {
    private ShoppingCartDao shoppingCartDao;
    private User userFromDb;
    private ShoppingCart shoppingCart;

    @Override
    protected Class<?>[] entities() {
        return new Class[]{ShoppingCart.class, User.class,
                Role.class, Ticket.class, MovieSession.class,
                Movie.class, CinemaHall.class};
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
        Ticket addedTicket = ticketDao.add(ticket);
        shoppingCartDao = new ShoppingCartDaoImpl(getSessionFactory());
        shoppingCart = new ShoppingCart();
        shoppingCart.setUser(userFromDb);
        shoppingCart.setTickets(List.of(addedTicket));
        shoppingCartDao.add(shoppingCart);
    }

    @Test
    void getShoppingCartByUser_Ok() {
        ShoppingCart shippingCartFromDb = shoppingCartDao.getByUser(userFromDb);
        Assertions.assertNotNull(shippingCartFromDb);
        int expectedId = 1;
        Assertions.assertEquals(expectedId, shippingCartFromDb.getId());
    }
}
