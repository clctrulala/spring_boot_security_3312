package spring.boot_security.demo.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }

    public GrantedAuthority giveAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }

}
