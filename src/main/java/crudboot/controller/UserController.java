package crudboot.controller;

import crudboot.model.Role;
import crudboot.model.User;

import crudboot.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.enterprise.inject.Model;
import java.util.*;

@Controller
public class UserController {

    final private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String printUserMenu(ModelMap model) {
        List<String> messages = new ArrayList<>();
        Map<String, String> menuMap = new LinkedHashMap<>();

        menuMap.put("Profile Info", "/user");                                                       // < security phase update
        messages.add("Hello! And welcome to Spring Boot CRUD!");                                    // < spring boot phase update

        model.addAttribute("pageTitle", "User menu page");              // < security phase update
        model.addAttribute("messages", messages);
        model.addAttribute("menuMap", menuMap);

        return "index";
    }

    // security update :: >>>
    @GetMapping("/user")
    public String printUserInfo(ModelMap model) {
        model.addAttribute("pageTitle", "User information-page");
        model.addAttribute("tableTitle", "About user");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("user", userService.getUserByName(authentication.getName()));

        return "/user";
    }

    @GetMapping("/admin")
    public String printAdminMenu(ModelMap model) {
        List<String> messages = new ArrayList<>();
        Map<String, String> menuMap = new LinkedHashMap<>();

        menuMap.put("Profile Info", "/user");
        menuMap.put("Users list", "/admin/userlist");
        messages.add("Hello! And welcome to Security CRUD!");

        model.addAttribute("pageTitle", "Admin menu page");
        model.addAttribute("messages", messages);
        model.addAttribute("menuMap", menuMap);

        return "/admin/index";
    }
    // security update :: <<<

    @GetMapping("/admin/userlist")                                                                  // < security phase update
    public String printUserList(ModelMap model) {
        model.addAttribute("userList", userService.getUserList());
        model.addAttribute("userEditFormUrl", "/admin/useredit");       // < security phase update
        model.addAttribute("userCreateFormUrl", "/admin/usernew");      // < security phase update
        model.addAttribute("userDeleteActionUrl", "/admin/userdelete"); // < security phase update

        return "admin/userlist";                                                                    // < security phase update
    }

    @GetMapping(value = "/admin/usernew")                                                           // < security phase update
    public String printNewUserForm(ModelMap model) {
        model.addAttribute("pageTitle", "Create new user");
        model.addAttribute("formTitle", "New user data:");
        model.addAttribute("formAction", "/admin/usersave");            // < security phase update
        model.addAttribute("submitButtonText", "create");
        model.addAttribute("user", new User());

        model.addAttribute("rolesList", userService.getAvailableRoles());             // < spring boot phase update

        return "admin/useredit";                                                                    // < security phase update
    }

    @GetMapping(value = "/admin/useredit" + "/{id}")                                                // < security phase update
    public String printEditUserForm(@PathVariable String id, ModelMap model) {
        model.addAttribute("pageTitle", "Edit user");
        model.addAttribute("formTitle", "User data:");
        model.addAttribute("formAction", "/admin/userupdate");          // < security phase update
        model.addAttribute("submitButtonText", "update");
        model.addAttribute("user", userService.getUserById(Long.parseLong(id)));
        model.addAttribute("showUserId", "true");

        model.addAttribute("rolesList", userService.getAvailableRoles());             // < spring boot phase update

        return "admin/useredit";                                                                    // < security phase update
    }

    @GetMapping(value = "/admin/userdelete" + "/{id}")                                              // < security phase update
    public String deleteUser(@PathVariable String id, ModelMap model) {
        userService.delete(userService.getUserById(Long.parseLong(id)));
        return "redirect:" + "/admin/userlist";                                                     // < security phase update
    }

    @RequestMapping(value = "/admin/usersave", method = RequestMethod.POST)                         // < security phase update
    public String createNewUser(@ModelAttribute("user") User newUser, ModelMap model) {
        userService.add(newUser);
        return "redirect:" + "/admin/userlist";                                                     // < security phase update
    }

    @RequestMapping(value = "/admin/userupdate", method = RequestMethod.POST)                       // < security phase update
    public String updateUser(@ModelAttribute("user") User user, ModelMap model) {
        userService.update(user);
        return "redirect:" + "/admin/userlist";                                                     // < security phase update
    }

}
