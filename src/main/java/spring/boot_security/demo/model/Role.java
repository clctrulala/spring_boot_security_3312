package spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table
public class Role implements GrantedAuthority {
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public Role(){}

    public Role(String role) {
        this.name = role;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Set<Role> getAllRoles(){ return Stream.of(ADMIN, USER).map(Role::new).collect(Collectors.toSet()); }

    @Override
    public String getAuthority() {
        return this.name;
    }

    public GrantedAuthority giveAuthority() {
        return this;
    }

    public boolean equals(Object obj) {
        if(null == obj) { return false; }
        if (this == obj) {
            return true;
        } else {
            return ((Role)obj).getClass() == Role.class && this.name.equals(((Role)obj).getName());
        }
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() { return this.name; }
}
