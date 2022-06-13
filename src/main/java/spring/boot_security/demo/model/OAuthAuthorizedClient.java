package spring.boot_security.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;

@Entity
@Table(name = "oauth2_authorized_client")
@IdClass(OAuthAuthorizedClientId.class)
@Getter
@Setter
public class OAuthAuthorizedClient implements Serializable {
    @Id
    @Column(name = "client_registration_id", length = 100, nullable = false)
    private String clientRegistrationId;

    @Id
    @Column(name = "principal_name", length = 200, nullable = false)
    private String principalName;

    @Column(name = "access_token_type", length = 100, nullable = false)
    private String accessTokenType;

    @Column(name = "access_token_value", nullable = false)
    private Blob accessTokenValue;

    @Column(name = "access_token_issued_at", nullable = false)
    private Timestamp accessTokenIssuedAt;

    @Column(name = "access_token_expires_at", nullable = false)
    private Timestamp accessTokenExpiresAt;

    @Column(name = "access_token_scopes", length = 1000)
    private String accessTokenScopes;

    @Column(name = "refresh_token_value")
    private Blob refreshTokenValue;

    @Column(name = "refresh_token_issued_at")
    private Timestamp refreshTokenIssuedAt;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private Timestamp createdAt;
}
