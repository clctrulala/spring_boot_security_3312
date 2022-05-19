package spring.boot_security.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;
import spring.boot_security.demo.model.User;

import java.util.List;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    @Override
    <T extends User> T save(T user);

    @Override
    List<User> findAll();

    Long deleteById(long Id);

    User findByEmail(String name);
}
