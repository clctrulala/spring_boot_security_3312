package spring.boot_security.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @Column
    private String name;

    public Role() {}

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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public static Set<Role> getAllRoles() { return Stream.of(ADMIN, USER).map(Role::new).collect(Collectors.toSet()); }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getAuthority() {
        return this.name;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public GrantedAuthority giveAuthority() {
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null != obj && obj instanceof GrantedAuthority) {
            return ((GrantedAuthority)obj).getAuthority().equals(name);
        }
        return false;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() { return this.name; }
}