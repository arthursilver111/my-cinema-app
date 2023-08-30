package cinema.service.mapper;

import cinema.dto.response.ShoppingCartResponseDto;
import cinema.model.ShoppingCart;
import cinema.model.Ticket;
import cinema.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;

class ShoppingCartMapperTest {
    private static ShoppingCartMapper shoppingCartMapper;

    @BeforeAll
    static void beforeAll() {
        shoppingCartMapper = new ShoppingCartMapper();
    }

    @Test
    void mapToDto_Ok() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        User user = new User();
        user.setId(1L);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setUser(user);
        shoppingCart.setTickets(List.of(ticket));

        ShoppingCartResponseDto shoppingCartResponseDto = shoppingCartMapper.mapToDto(shoppingCart);
        Assertions.assertNotNull(shoppingCartResponseDto);
        Assertions.assertEquals(shoppingCart.getUser().getId(), shoppingCartResponseDto.getUserId());
        Assertions.assertEquals(ticket.getId(), shoppingCart.getTickets().get(0).getId());
    }
}
