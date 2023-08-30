package cinema.dao;

import cinema.dao.impl.RoleDaoImpl;
import cinema.dao.impl.UserDaoImpl;
import cinema.exception.DataProcessingException;
import cinema.model.Role;
import cinema.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.Set;

class UserDaoTest extends AbstractTest {
    private UserDao userDao;
    private User user;

    @Override
    protected Class<?>[] entities() {
        return new Class[]{User.class, Role.class};
    }

    @BeforeEach
    void setUp() {
        Role roleUser = new Role();
        roleUser.setRoleName(Role.RoleName.USER);
        RoleDao roleDao = new RoleDaoImpl(getSessionFactory());
        Role addedRole = roleDao.add(roleUser);

        userDao = new UserDaoImpl(getSessionFactory());
        user = new User();
        user.setEmail("fff123a");
        user.setPassword("123456");
        user.setRoles(Set.of(addedRole));
        user = userDao.add(user);
    }

    @Test
    void getEmail_Ok() {
        Optional<User> byEmail = userDao.findByEmail(user.getEmail());
        Assertions.assertTrue(byEmail.isPresent());
    }

    @Test
    void addUser_Ok() {
        Assertions.assertNotNull(user);
        int expectedId = 1;
        Assertions.assertEquals(expectedId, user.getId());
    }

    @Test
    void addUser_NotOk() {
        Assertions.assertThrows(DataProcessingException.class, () -> userDao.add(user));
    }

    @Test
    void get_Ok() {
        Optional<User> userFromDb = userDao.get(1L);
        Assertions.assertTrue(userFromDb.isPresent());
    }
}
