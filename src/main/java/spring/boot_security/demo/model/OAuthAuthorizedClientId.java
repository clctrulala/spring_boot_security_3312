package spring.boot_security.demo.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OAuthAuthorizedClientId implements Serializable {
    private String clientRegistrationId;
    private String principalName;
}
