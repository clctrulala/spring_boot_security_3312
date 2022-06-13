package spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import spring.boot_security.demo.model.User;

@Service
@AllArgsConstructor
public class OAuthService {
    private final UserDetailsService userDetailsService;

    public void checkUser(User user) {
        UserDetails currentUser = userDetailsService.loadUserByUsername(user.getEmail());

        if(null != currentUser) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication token = new UsernamePasswordAuthenticationToken(currentUser,
                    null, currentUser.getAuthorities());
            context.setAuthentication(token);
            SecurityContextHolder.setContext(context);
        }
    }
}
