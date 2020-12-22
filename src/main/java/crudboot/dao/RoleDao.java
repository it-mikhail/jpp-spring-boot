package crudboot.dao;

import crudboot.model.Role;

import java.util.List;

public interface RoleDao {

    void add(Role role);
    Role get(String role);

    // spring boot phase update :: >>>
    List<Role> getRolesList();
    Role get(Long roleId);
    // spring boot phase update :: <<<

}
