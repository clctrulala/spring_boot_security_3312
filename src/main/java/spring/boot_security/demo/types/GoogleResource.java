package spring.boot_security.demo.types;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GoogleResource {
    private String resourceName;
    private String etag;
    private List<GoogleResourceElementNames> names;
    private List<GoogleResourceElementEmailAddress> emailAddresses;
    private List<GoogleResourceElementAgeRanges> ageRanges;
}
