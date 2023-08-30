package cinema.service;

import cinema.dao.RoleDao;
import cinema.exception.DataProcessingException;
import cinema.model.Role;
import cinema.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;

class RoleServiceTest {
    private static RoleService roleService;
    private static RoleDao roleDao;
    private Role role;

    @BeforeAll
    static void beforeAll() {
        roleDao = Mockito.mock(RoleDao.class);
        roleService = new RoleServiceImpl(roleDao);
    }

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1L);
        role.setRoleName(Role.RoleName.USER);
    }

    @Test
    void add_Ok() {
        Mockito.when(roleDao.add(role)).thenReturn(role);
        Role addedRole = roleService.add(role);
        Assertions.assertNotNull(addedRole);
    }

    @Test
    void getByName_Ok() {
        Mockito.when(roleDao.getByName(Role.RoleName.USER)).thenReturn(Optional.ofNullable(role));
        Role roleFromDb = roleService.getByName(Role.RoleName.USER);
        Assertions.assertNotNull(roleFromDb);
        Assertions.assertEquals(role.getRoleName(), roleFromDb.getRoleName());
    }

    @Test
    void getByName_NotOk() {
        Mockito.when(roleDao.getByName(Role.RoleName.USER)).thenThrow(new DataProcessingException("Could not get role"));
        Assertions.assertThrows(DataProcessingException.class, () -> roleService.getByName(Role.RoleName.USER));
    }
}
