package spring.boot_security.demo.service;

import spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    boolean addUser(User user);
    List<User> getUsers();
    User getUser(Long id);
    boolean updateUser(User user);
    boolean deleteUser(long id);
}
