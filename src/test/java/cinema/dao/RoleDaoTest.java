package cinema.dao;

import cinema.dao.impl.RoleDaoImpl;
import cinema.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

class RoleDaoTest extends AbstractTest {
    private RoleDao roleDao;

    @Override
    protected Class<?>[] entities() {
        return new Class[]{Role.class};
    }

    @BeforeEach
    void setUp() {
        roleDao = new RoleDaoImpl(getSessionFactory());
        Role role = new Role();
        role.setRoleName(Role.RoleName.USER);
        roleDao.add(role);
    }

    @Test
    void getOrdersHistory_Ok() {
        Optional<Role> roleUser = roleDao.getByName(Role.RoleName.USER);
        Assertions.assertTrue(roleUser.isPresent());
    }
}
