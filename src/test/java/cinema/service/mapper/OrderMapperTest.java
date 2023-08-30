package cinema.service.mapper;

import cinema.dto.response.OrderResponseDto;
import cinema.model.Order;
import cinema.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

class OrderMapperTest {
    private static OrderMapper orderMapper;

    @BeforeAll
    static void beforeAll() {
        orderMapper = new OrderMapper();
    }

    @Test
    void mapToDto_Ok() {
        User user = new User();
        user.setPassword("123");
        user.setEmail("art@gmail.com");
        user.setId(1L);

        Order order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setTickets(List.of());
        order.setOrderTime(LocalDateTime.now());

        OrderResponseDto orderResponseDto = orderMapper.mapToDto(order);
        Assertions.assertNotNull(orderResponseDto);
        Assertions.assertEquals(order.getId(), orderResponseDto.getId());
        Assertions.assertEquals(order.getUser().getId(), orderResponseDto.getUserId());
    }
}
