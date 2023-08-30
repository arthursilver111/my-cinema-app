package cinema.service;

import cinema.dao.ShoppingCartDao;
import cinema.dao.TicketDao;
import cinema.model.ShoppingCart;
import cinema.model.User;
import cinema.service.impl.ShoppingCartServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ShoppingCartServiceTest {
    private static ShoppingCartService shoppingCartService;
    private static ShoppingCartDao shoppingCartDao;

    @BeforeAll
    static void beforeAll() {
        TicketDao ticketDao = Mockito.mock(TicketDao.class);
        shoppingCartDao = Mockito.mock(ShoppingCartDao.class);
        shoppingCartService = new ShoppingCartServiceImpl(shoppingCartDao, ticketDao);
    }

    @Test
    void getByUser_Ok() {
        User user = new User();
        user.setId(1L);
        user.setEmail("art@gmail.com");
        user.setPassword("1234");

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setId(1L);
        Mockito.when(shoppingCartDao.getByUser(user)).thenReturn(shoppingCart);
        ShoppingCart shoppingCartFromDb = shoppingCartService.getByUser(user);
        Assertions.assertNotNull(shoppingCartFromDb);
        Assertions.assertEquals(shoppingCart.getUser(), shoppingCartFromDb.getUser());
    }
}
