package cinema.service;

import cinema.dao.UserDao;
import cinema.model.User;
import cinema.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

class UserServiceTest {
    private static UserService userService;
    private static PasswordEncoder encoder;
    private static UserDao userDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        encoder = Mockito.mock(PasswordEncoder.class);
        userDao = Mockito.mock(UserDao.class);
        userService = new UserServiceImpl(encoder, userDao);
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setPassword("1234");
        user.setEmail("abc");
        user.setId(1L);
    }

    @Test
    void add_Ok() {
        String expectedPassword = "ab12";
        Mockito.when(encoder.encode(any())).thenReturn(expectedPassword);
        Mockito.when(userDao.add(user)).thenReturn(user);
        User addedUser = userService.add(user);
        Assertions.assertNotNull(addedUser);
        Assertions.assertEquals(expectedPassword, addedUser.getPassword());
    }

    @Test
    void getById_Ok() {
        Mockito.when(userDao.get(1L)).thenReturn(Optional.of(user));
        User userFromDb = userService.get(1L);
        Assertions.assertNotNull(userFromDb);
    }

    @Test
    void getById_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> userService.get(2L));
    }

    @Test
    void findByEmail_Ok() {
        Mockito.when(userDao.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Optional<User> userFromDb = userService.findByEmail(user.getEmail());
        Assertions.assertTrue(userFromDb.isPresent());
    }

    @Test
    void findByEmail_NotOk() {
        Mockito.when(userDao.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        Optional<User> userFromDb = userService.findByEmail(user.getEmail());
        Assertions.assertTrue(userFromDb.isEmpty());
    }
}
