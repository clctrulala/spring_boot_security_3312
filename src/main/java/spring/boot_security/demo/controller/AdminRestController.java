package spring.boot_security.demo.controller;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import spring.boot_security.demo.model.User;
import spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AdminRestController {
    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody Optional<User> user) {
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        try{
            Optional<User> response = Optional.of(userService.addUser(user.get()));

            user.get().setPassword("");
            return new ResponseEntity<>(response.get(), HttpStatus.CREATED);
        } catch (NullPointerException err) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@RequestBody Optional<User> user) {
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userService.updateUser(user.get()), HttpStatus.OK);
    }

    @DeleteMapping(value = "delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity removeUser(@RequestBody Optional<User> user) {
        if(user.isEmpty() || null == user.get().getId()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        userService.deleteUser(user.get().getId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity removeUserById(@PathVariable Optional<Long> id) {
        if(id.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        userService.deleteUser(id.get());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
