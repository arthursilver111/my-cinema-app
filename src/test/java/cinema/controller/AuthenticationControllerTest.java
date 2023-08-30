package cinema.controller;

import cinema.dto.request.UserRequestDto;
import cinema.dto.response.UserResponseDto;
import cinema.model.Role;
import cinema.model.User;
import cinema.service.AuthenticationService;
import cinema.service.mapper.ResponseDtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Set;

class AuthenticationControllerTest {
    private static AuthenticationController authenticationController;
    private static AuthenticationService authService;
    private static ResponseDtoMapper<UserResponseDto, User> userDtoResponseMapper;

    @BeforeAll
    static void beforeAll() {
        authService = Mockito.mock(AuthenticationService.class);
        userDtoResponseMapper = Mockito.mock(ResponseDtoMapper.class);
        authenticationController = new AuthenticationController(authService, userDtoResponseMapper);
    }

    @Test
    void register_Ok() throws NoSuchFieldException, IllegalAccessException {
        UserRequestDto requestDto = new UserRequestDto();

        Class<?> requestDtoClass = requestDto.getClass();
        Field fieldEmail = requestDtoClass.getDeclaredField("email");
        fieldEmail.setAccessible(true);
        fieldEmail.set(requestDto, "abc@gmail.com");

        Field fieldPassword = requestDtoClass.getDeclaredField("password");
        fieldPassword.setAccessible(true);
        fieldPassword.set(requestDto, "12345678");

        Field repeatPassword = requestDtoClass.getDeclaredField("repeatPassword");
        repeatPassword.setAccessible(true);
        repeatPassword.set(requestDto, "12345678");

        Role roleUser = new Role();
        roleUser.setId(1L);
        roleUser.setRoleName(Role.RoleName.USER);

        User user = new User();
        user.setEmail("abc@gmail.com");
        user.setPassword("12345678");
        user.setId(1L);
        user.setRoles(Set.of(roleUser));

        UserResponseDto expectedUserResponseDto = new UserResponseDto();
        expectedUserResponseDto.setId(user.getId());
        expectedUserResponseDto.setEmail(user.getEmail());

        Mockito.when(authService.register(requestDto.getEmail(), requestDto.getPassword())).thenReturn(user);
        Mockito.when(userDtoResponseMapper.mapToDto(user)).thenReturn(expectedUserResponseDto);

        UserResponseDto registeredUser = authenticationController.register(requestDto);
        Assertions.assertEquals(expectedUserResponseDto.getEmail(), registeredUser.getEmail());
        Assertions.assertEquals(expectedUserResponseDto.getId(), registeredUser.getId());
    }
}
