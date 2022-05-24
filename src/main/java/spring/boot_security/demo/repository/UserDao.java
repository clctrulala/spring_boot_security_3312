package spring.boot_security.demo.repository;

import com.sun.istack.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;
import spring.boot_security.demo.model.User;

import java.util.List;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    List<User> findAll();

    @Query("select distinct u from User u join fetch u.roles")
    List<User> findUsers();

    @Query("select u from User u join fetch u.roles where u.email=:email")
    User findByEmail(String email);
}
