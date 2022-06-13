package spring.boot_security.demo.types;

public class OAuthAuthorizationException extends RuntimeException {
    public OAuthAuthorizationException(String message) {
        super(message);
    }

    public OAuthAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
