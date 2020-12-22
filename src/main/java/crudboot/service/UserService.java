package crudboot.service;

import crudboot.model.Role;
import crudboot.model.User;

import java.util.List;

public interface UserService {

    void add(User user);
    void update(User user);
    void delete(User user);
    User getUserById(Long userId);
    List<User> getUserList();

    // security phase update :: >>>
    User getUserByName(String userEmail);                                                       // << spring boot phase update
    void addRole(Role role);
    // security phase update :: <<<

    // spring boot phase boot :: >>>
    List<Role> getAvailableRoles();
    User updateUserRoles(User user);
    // spring boot phase boot :: <<<

}
