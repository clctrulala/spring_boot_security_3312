package spring.boot_security.demo.service;

import spring.boot_security.demo.model.Role;
import spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User addUser(User user) throws Exception;
    List<User> getUsers();
    User getUser(Long id);
    User updateUser(User user);
    void deleteUser(long id);
    Set<Role> getAllRoles();
}
