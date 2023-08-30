package cinema.service;

import cinema.model.Role;
import cinema.model.User;
import cinema.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Set;
import static org.mockito.ArgumentMatchers.any;

class AuthenticationServiceTest {
    private static AuthenticationService authenticationService;
    private static UserService userService;
    private static RoleService roleService;

    @BeforeAll
    static void beforeAll() {
        userService = Mockito.mock(UserService.class);
        ShoppingCartService shoppingCartService = Mockito.mock(ShoppingCartService.class);
        roleService = Mockito.mock(RoleService.class);
        authenticationService = new AuthenticationServiceImpl(userService, shoppingCartService, roleService);
    }

    @Test
    void register_Ok() {
        String email = "bob@gmail.com";
        String password = "1234";
        Role roleUser = new Role();
        roleUser.setId(1L);
        roleUser.setRoleName(Role.RoleName.USER);

        User user = new User();
        user.setId(1L);
        user.setPassword(password);
        user.setEmail(email);
        user.setRoles(Set.of(roleUser));

        Mockito.when(roleService.getByName(Role.RoleName.USER)).thenReturn(roleUser);
        Mockito.when(userService.add(any())).thenReturn(user);
        User registeredUser = authenticationService.register(email, password);
        int expectedId = 1;
        Assertions.assertNotNull(registeredUser);
        Assertions.assertEquals(expectedId, registeredUser.getId());
    }
}
