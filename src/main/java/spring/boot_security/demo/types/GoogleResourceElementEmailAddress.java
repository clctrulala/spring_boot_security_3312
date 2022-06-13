package spring.boot_security.demo.types;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoogleResourceElementEmailAddress {
    private GoogleResourceMetadata metadata;
    private String value;
    private String type;
    private String formattedType;
    private String displayName;
}
