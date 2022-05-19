package spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.boot_security.demo.model.User;
import spring.boot_security.demo.repository.UserDao;

import java.util.List;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userDao.findByEmail(username);

        if(null == user) {
            throw new UsernameNotFoundException("User not found in database");
        }
        return user;
    }
    @Override
    public boolean addUser(User user) {
        return null != userDao.save(user);
    }

    @Override
    public List<User> getUsers() {
        List<User> users = userDao.findAll();

        return users.stream()
                .peek(user -> user.setPassword(""))
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(User user) {
        if (null != user.getPassword()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if(null != user.getId()) {
            if( null == user.getPassword()) { user.setPassword(getUser(user.getId()).getPassword()); }
        }
        userDao.save(user);
    }

    @Override
    public void deleteUser(long id) {
        userDao.deleteById(id);
    }

    @Override
    public User getUser(Long id) {
        return userDao.findById(id).orElse(null);
    }
}
