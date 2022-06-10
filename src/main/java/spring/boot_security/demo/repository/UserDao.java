package spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {
    List<User> findAll();

    @EntityGraph(attributePaths = "roles")
    User findByEmail(String email);

    User findByFirstNameAndLastName(String firstName, String lastName);
}
