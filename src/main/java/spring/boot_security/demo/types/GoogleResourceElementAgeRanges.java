package spring.boot_security.demo.types;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoogleResourceElementAgeRanges {
    private GoogleResourceMetadata metadata;
    private GoogleAgeRange ageRange;
}
