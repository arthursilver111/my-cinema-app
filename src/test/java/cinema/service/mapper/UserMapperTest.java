package cinema.service.mapper;

import cinema.dto.response.UserResponseDto;
import cinema.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserMapperTest {
    private static UserMapper userMapper;

    @BeforeAll
    static void beforeAll() {
        userMapper = new UserMapper();
    }

    @Test
    void mapToDto_Ok() {
        User user = new User();
        user.setPassword("123");
        user.setEmail("art@gmail.com");
        user.setId(1L);
        UserResponseDto userResponseDto = userMapper.mapToDto(user);
        Assertions.assertNotNull(userResponseDto);
        Assertions.assertEquals(user.getId(),userResponseDto.getId());
    }
}
