package spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import spring.boot_security.demo.model.Role;
import spring.boot_security.demo.model.User;
import spring.boot_security.demo.repository.UserDao;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FillTestEntities implements ApplicationRunner {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(ApplicationArguments args) {
        userDao.save( new User("Jhon Connor", new Date(1984-1900, 00, 01), Gender.MALE, +79001234567L, encoder.encode("t1000"), Stream.of(Role.ADMIN).map(Role::new).collect(Collectors.toSet())));
        userDao.save( new User("Sara Connor", new Date(1967-1900, 06, 05), Gender.FEMALE, +79007654321L, encoder.encode("t800"),  Stream.of(Role.USER, Role.ADMIN).map(Role::new).collect(Collectors.toSet())));
        userDao.save( new User("user", new Date(2022-1900, 11, 12), Gender.MALE, +79991234567L, encoder.encode("user"),  Stream.of(Role.USER).map(Role::new).collect(Collectors.toSet())));
        userDao.save( new User("admin", new Date(1900-1900, 00, 01), Gender.MALE, +79991234567L, encoder.encode("admin"),  Stream.of(Role.USER, Role.ADMIN).map(Role::new).collect(Collectors.toSet())));
    }
}
