package crudboot.dao;

import crudboot.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role get(String roleName) {
        return (Role) entityManager.createQuery("select r from Role r where role=:roleName")
                .setParameter("roleName", roleName)
                .getSingleResult();
    }

    // spring boot phase update :: >>>
    public List<Role> getRolesList() {
        return entityManager.createQuery("select r from Role r")
                .getResultList();
    }

    public Role get(Long roleId) {
        return (Role) entityManager.createQuery("select f from Role r where id=:roleId")
                .setParameter("roleId", roleId)
                .getSingleResult();
    }
    // spring boot phase update :: <<<

}
