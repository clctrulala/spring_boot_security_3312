package spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot_security.demo.model.OAuthAuthorizedClientId;
import spring.boot_security.demo.model.OAuthAuthorizedClient;

public interface C_ClientRegistrationRepository extends JpaRepository<OAuthAuthorizedClient, OAuthAuthorizedClientId> {
}
