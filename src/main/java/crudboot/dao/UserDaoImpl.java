package crudboot.dao;

import org.springframework.stereotype.Repository;

import crudboot.model.User;
import crudboot.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public User getUserById(Long userId) {
        return entityManager.find(User.class, userId);
    }

    @Override
    public List<User> getUserList() {
        return entityManager.createQuery("select u from User u")
                .getResultList();
    }

    // spring boot phase update :: >>>
    public User getUserByEmail(String userEmail) {
        return (User) entityManager.createQuery("select u from User u where email=:email")
                .setParameter("email", userEmail)
                .getSingleResult();
    }
    // spring boot phase update :: <<<

    // security update :: >>> // << spring boot phase update (commented)
    // @Override
    // public User getUserByName(String userName) {
    //     return (User) entityManager.createQuery("select u from User u where name=:name")
    //             .setParameter("name", userName)
    //             .getSingleResult();
    // }
    // security update :: <<<

}
