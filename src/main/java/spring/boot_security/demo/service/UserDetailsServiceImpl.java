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
import spring.boot_security.demo.repository.RoleDao;
import spring.boot_security.demo.repository.UserDao;

import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleDao roleDao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, RoleDao roleDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Role> getAllRoles() {
        return roleDao.findAll();
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
    public User addUser(User user) throws Exception {
        if(null == user.getRoles() || 0 == user.getRoles().size()) {
            throw new NullPointerException("User without role");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(null != userDao.findByFirstNameAndLastName(user.getFirstName(), user.getLastName())) {
            throw new Exception("Nonunique user names");
        }
        return userDao.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        User oldUser = getUser(user.getId());

        if(user.getPassword().isEmpty()) {
            user.setPassword(oldUser.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if(user.getRoles().isEmpty()) {
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
        return userDao.findById(id).orElseThrow();
    }
}
