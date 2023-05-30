package cinema.dao.impl;

import cinema.dao.AbstractDao;
import cinema.exception.DataProcessingException;
import cinema.model.Role;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDao extends AbstractDao<Role> implements cinema.dao.RoleDao {

    public RoleDao(SessionFactory factory) {
        super(factory, Role.class);
    }

    @Override
    public Optional<Role> getByName(Role.RoleName roleName) {
        try (Session session = factory.openSession()) {
            Query<Role> findByRole = session.createQuery(
                    "FROM Role  WHERE roleName = :roleName", Role.class);
            findByRole.setParameter("roleName", roleName);
            return findByRole.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Role: " + roleName + " not found", e);
        }
    }
}
