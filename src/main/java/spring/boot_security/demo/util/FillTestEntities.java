package spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import spring.boot_security.demo.model.User;
import spring.boot_security.demo.repository.UserDao;

import java.util.Date;
import java.util.Set;

@Component
public class FillTestEntities implements ApplicationRunner {

        @Autowired
        private UserDao userDao;
        @Autowired
        private PasswordEncoder encoder;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            userDao.save( new User("Jhon","Connor", (byte)16, "jhon@example.com", encoder.encode("t1000"), Set.of(Role.ADMIN)));
            userDao.save( new User("Sara", "Connor", (byte)24, "sara@example.com", encoder.encode("t800"),  Set.of(Role.USER, Role.ADMIN)));
            userDao.save( new User("user", "resu", (byte)21, "user@mail.ru", encoder.encode("user"),  Set.of(Role.USER)));
            userDao.save( new User("admin", "nimda", (byte)21,"admin@mail.ru", encoder.encode("admin"),  Set.of(Role.USER, Role.ADMIN)));
        }
}
