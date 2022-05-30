package spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao extends CrudRepository<User, Long> {
    List<User> findAll();

    @Query("select u from User u join fetch u.roles where u.email=:email")
    User findByEmail(String email);
}
