package spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.boot_security.demo.model.Role;
import spring.boot_security.demo.model.User;
import spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AdminRestController {
    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("permits")
    public ResponseEntity<Set<Role>> getPermits() {
        return new ResponseEntity<>(userService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("list")
    public ResponseEntity<List<User>> getUserList() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE, params = "user!=")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try{
            return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
        } catch(Exception err) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE, params = "user!=")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        try{
             return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
        } catch(Exception err) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, params = "id!=")
    public ResponseEntity<HttpStatus> removeUserById(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception err) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
