package cinema.security;

import cinema.model.Role;
import cinema.model.User;
import cinema.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;
import java.util.Set;

class CustomUserDetailsServiceTest {
    private static UserDetailsService userDetailsService;
    private static UserService userService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        userService = Mockito.mock(UserService.class);
        userDetailsService = new CustomUserDetailsService(userService);
    }

    @BeforeEach
    void setUp() {
        Role roleUser = new Role();
        roleUser.setRoleName(Role.RoleName.USER);
        user = new User();
        user.setPassword("1234");
        user.setEmail("abc@gmail.com");
        user.setRoles(Set.of(roleUser));
        user.setId(1L);
    }

    @Test
    void loadUserByUsername_Ok() {
        Mockito.when(userService.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(user.getEmail(), userDetails.getUsername());
        Assertions.assertEquals(user.getPassword(), userDetails.getPassword());
    }

    @Test
    void loadUserByUsername_NotOk() {
        Mockito.when(userService.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(user.getEmail()));
    }
}
