package crudboot.service;

import crudboot.dao.RoleDao;
import crudboot.model.Role;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import crudboot.dao.UserDao;
import crudboot.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final RoleDao roleDao;                                                              // < security phase update

    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {                                  // < security phase update
        this.userDao = userDao;
        this.roleDao = roleDao;                                                                 // < security phase update
    }

    // spring boot phase update :: >>>
    @Transactional
    @Override
    public User updateUserRoles(User user) {
        boolean updateRoles = false;
        Set<Role> rolesUpdateSet = new HashSet<>();

        for (Role role : user.getRoles()) {
            if (role.getId() == null) {
                updateRoles = true;
                rolesUpdateSet.add(roleDao.get(role.getRole()));
            } else {
                rolesUpdateSet.add(role);
            }
        }

        if (updateRoles) {
            user.setRoles(rolesUpdateSet);
        }

        return user;
    }
    // spring boot phase update :: <<<

    @Transactional
    @Override
    public void add(User user) {
        userDao.add(updateUserRoles(user));                                                     // << spring boot phase update
    }

    @Transactional
    @Override
    public void update(User user) {
        userDao.update(updateUserRoles(user));                                                  // << spring boot phase update
    }

    @Transactional
    @Override
    public void delete(User user) {
        userDao.delete(userDao.getUserById(user.getId()));
    }

    @Transactional
    @Override
    public User getUserById(Long userId) {
        return userDao.getUserById(userId);
    }

    @Transactional
    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }

    // security phase update :: >>>
    @Transactional
    @Override
    public User getUserByName(String userEmail) {
        return userDao.getUserByEmail(userEmail);                                               // < spring boot phase update
    }

    @Transactional
    @Override
    public void addRole(Role role) {
        roleDao.add(role);
    }
    // security phase update :: <<<

    // spring boot phase update :: >>>
    @Transactional
    @Override
    public List<Role> getAvailableRoles() {
        return roleDao.getRolesList();
    }
    // spring boot phase update :: <<<

}
