package spring.boot_security.demo.service;

import spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    List<User> getUsers();
    User getUser(Long id);
    User updateUser(User user);
    void deleteUser(long id);
}
