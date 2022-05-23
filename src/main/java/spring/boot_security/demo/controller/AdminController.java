package spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.boot_security.demo.model.Role;
import spring.boot_security.demo.model.User;
import spring.boot_security.demo.service.UserService;
import spring.boot_security.demo.util.Gender;

import java.util.Date;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getUsers(ModelMap model) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("permits", Role.getAllRoles());
        return "persons";
    }

    @GetMapping(value = "add", params = {"name!=", "birthdate!=", "gender!=", "phone!=", "password!=", "role!="})
    public String addUser(String name, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthdate, Gender gender, @NumberFormat(pattern="7[0-9]{10}") Long phone, String password, @RequestParam Set<Role> role, ModelMap model) {
        User user = new User(name, birthdate, gender, phone, passwordEncoder.encode(password), role);
        userService.addUser(user);
        return getUsers(model);
    }

    @GetMapping(value = "update", params = {"id!=", "name!=", "birthdate!=", "gender!=", "phone!=", "role!="})
    public String updateUser(Long id, String name, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthdate, Gender gender, @NumberFormat(pattern="7[0-9]{10}") Long phone, @RequestParam Set<Role> role, ModelMap model) {
        User user = new User(id, name, birthdate, gender, phone, passwordEncoder.encode( "password"), role);
        userService.updateUser(user);
        return getUsers(model);
    }

    @GetMapping("delete")
    public String removeUser(long id, ModelMap model) {
        userService.deleteUser(id);
        return getUsers(model);
    }
}
