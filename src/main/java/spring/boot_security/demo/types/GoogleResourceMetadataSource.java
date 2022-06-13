package spring.boot_security.demo.types;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoogleResourceMetadataSource {
    private String type;
    private String id;
}
