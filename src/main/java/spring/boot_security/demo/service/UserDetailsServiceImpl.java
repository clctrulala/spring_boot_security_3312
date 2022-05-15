package spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.boot_security.demo.model.User;
import spring.boot_security.demo.repository.UserDao;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService {
    private final UserDao userDao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userDao.findByName(username);

        if(null == user) {
            throw  new UsernameNotFoundException("User not found in database");
        }

        return user;
    }
    @Override
    public boolean addUser(User user) {
        return null != userDao.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    public boolean updateUser(User user) {
        User old = getUser(user.getId());
        if ( null != old ) {
            user.setPassword( old.getPassword() );
            userDao.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(long id) {
        if ( userDao.existsById(id) ) {
            userDao.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public User getUser(Long id) {
        return userDao.findById(id).orElse(null);
    }
}
