package spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.boot_security.demo.model.Role;
import spring.boot_security.demo.model.User;
import spring.boot_security.demo.repository.UserDao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userDao.findByEmail(username);

        if(null == user) {
            throw new UsernameNotFoundException("User not found in database");
        }
        return user;
    }
    @Override
    @Transactional
    public User addUser(User user) {
        if(null == user.getRoles() || 0 == user.getRoles().size()) {
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userDao.findAll().stream().peek(user -> user.setPassword("")).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        Optional<String> password = Optional.of(user.getPassword());
        User oldUser = getUser(user.getId());

        if (password.isEmpty() || password.get().isEmpty()) {
            user.setPassword(oldUser.getPassword());
        }
        if(null == user.getRoles() || 0 == user.getRoles().size()) {
            user.setRoles(oldUser.getRoles());
        }
        return userDao.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userDao.findById(id).orElse(null);
    }
}
