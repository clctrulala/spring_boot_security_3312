package spring.boot_security.demo.types;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoogleResourceElementNames {
    private GoogleResourceMetadata metadata;
    private String displayName;
    private String familyName;
    private String givenName;
    private String displayNameLastFirst;
    private String unstructuredName;
}
