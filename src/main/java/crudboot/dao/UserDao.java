package crudboot.dao;

import crudboot.model.Role;
import crudboot.model.User;
import java.util.List;

public interface UserDao {
     void add(User user);
     void update(User user);
     void delete(User user);
     User getUserById(Long userId);
     List<User> getUserList();

     // spring boot phase update :: >>>
     User getUserByEmail(String userEmail);
     // spring boot phase update :: <<<

     // security phase update :: >>>
     // User getUserByName(String userName); // << spring boot phase update (commented)
     // security phase update :: <<<

}
