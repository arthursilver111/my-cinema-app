package cinema.service;

import cinema.dao.OrderDao;
import cinema.model.Order;
import cinema.model.ShoppingCart;
import cinema.model.Ticket;
import cinema.model.User;
import cinema.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import java.util.List;

class OrderServiceTest {
    private static OrderService orderService;
    private static OrderDao orderDao;
    private static ShoppingCartService shoppingCartService;
    private Order order;
    private Ticket ticket;
    private User user;

    @BeforeAll
    static void beforeAll() {
        orderDao = Mockito.mock(OrderDao.class);
        shoppingCartService = Mockito.mock(ShoppingCartService.class);
        orderService = new OrderServiceImpl(orderDao, shoppingCartService);
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("art@gmail.com");
        user.setPassword("1234");

        order = new Order();
        order.setUser(user);
        order.setOrderTime(LocalDateTime.now());
        ticket = new Ticket();
        ticket.setId(1L);
        order.setTickets(List.of(ticket));
    }

    @Test
    void completeOrder_Ok() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        Order completedOrder = orderService.completeOrder(shoppingCart);
        Assertions.assertNotNull(completedOrder);
        Assertions.assertEquals(ticket.getId(), order.getTickets().get(0).getId());
    }

    @Test
    void getOrdersHistory_Ok() {
        List<Order> expectedOrderHistory = List.of(order);
        Mockito.when(orderDao.getOrdersHistory(user)).thenReturn(expectedOrderHistory);
        List<Order> ordersHistoryFromDb = orderService.getOrdersHistory(user);
        Assertions.assertNotNull(ordersHistoryFromDb);
        Assertions.assertEquals(expectedOrderHistory, ordersHistoryFromDb);
    }

    @Test
    void getOrdersHistory_NotOk() {
        Mockito.when(orderService.getOrdersHistory(user)).thenThrow();
        Assertions.assertThrows(RuntimeException.class, () -> orderService.getOrdersHistory(user));
    }
}
