package spring.boot_security.demo.repository;

import org.springframework.data.repository.CrudRepository;
import spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleDao extends CrudRepository<Role, Long> {
    Set<Role> findAll();
}
