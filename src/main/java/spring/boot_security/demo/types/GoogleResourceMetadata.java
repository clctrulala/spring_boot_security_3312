package spring.boot_security.demo.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoogleResourceMetadata {
    private GoogleResourceMetadataSource source;
    private Boolean primary;
    private Boolean verified;
    private Boolean sourcePrimary;
}
