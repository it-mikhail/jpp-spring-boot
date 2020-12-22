package crudboot.service;

import crudboot.dao.UserDao;
import crudboot.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {  // << spring boot phase update
        User user = userDao.getUserByEmail(userEmail);                                          // << spring boot phase update
        if (user == null) {
            throw new UsernameNotFoundException(userEmail);                                     // << spring boot phase update
        }
        return user;
    }

}
