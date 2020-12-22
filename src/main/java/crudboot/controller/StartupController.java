package crudboot.controller;

import crudboot.model.Role;
import crudboot.model.User;
import crudboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.Set;

@Controller
public class StartupController {
    // for testing, "preinstalled" roles and users

    final private UserService userService;

    @Autowired
    public StartupController(UserService userService) {
        this.userService = userService;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Role userRole = new Role();
        userRole.setRole("ROLE_USER");
        Role adminRole = new Role();
        adminRole.setRole("ROLE_ADMIN");

        userService.addRole(userRole);
        userService.addRole(adminRole);

        Set<Role> userRolesSet = new HashSet<>();
        Set<Role> adminRolesSet = new HashSet<>();

        userRolesSet.add(userRole);

        adminRolesSet.add(userRole);
        adminRolesSet.add(adminRole);

        User user = new User("userFirstName", "userLastName", "user@localhost", 35,"user", userRolesSet);
        User admin = new User("adminFirstName", "adminLastName", "admin@localhost", 35, "admin", adminRolesSet);

        userService.add(user);
        userService.add(admin);
    }

}
