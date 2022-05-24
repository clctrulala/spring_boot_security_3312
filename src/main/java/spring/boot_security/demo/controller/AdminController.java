package spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.boot_security.demo.model.Role;
import spring.boot_security.demo.model.User;
import spring.boot_security.demo.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserDetailsService detailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, UserDetailsService detailsService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.detailsService = detailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getUsers(ModelMap model, Authentication auth) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("permits", Role.getAllRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        model.addAttribute("current_user", detailsService.loadUserByUsername(auth.getName()));
        return "users_list";
    }

    @GetMapping(value = "update", params = {"first!=", "last!=", "age!=", "email!=", "role!="})
    public String updateUser(@RequestParam(required = false) Long id, @RequestParam("first") String firstName, @RequestParam("last") String lastName, byte age, String email, @RequestParam(required = false) String password, @RequestParam Set<Role> role, ModelMap model, Authentication auth) {
        userService.updateUser(new User(id, firstName, lastName, age, email, password, role));
        return getUsers(model, auth);
    }

    @GetMapping("delete/{id}")
    public String removeUser(@PathVariable long id, ModelMap model, Authentication auth) {
        userService.deleteUser(id);
        return getUsers(model, auth);
    }
}
