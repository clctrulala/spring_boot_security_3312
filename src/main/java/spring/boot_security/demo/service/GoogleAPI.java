package spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spring.boot_security.demo.model.Role;
import spring.boot_security.demo.model.User;
import spring.boot_security.demo.security.OAuthGoogle;
import spring.boot_security.demo.types.GoogleResource;
import spring.boot_security.demo.types.GoogleResourceElementEmailAddress;
import spring.boot_security.demo.types.GoogleResourceElementNames;
import spring.boot_security.demo.types.OAuthAuthorizationException;

import java.util.Set;

@Service
@AllArgsConstructor
public class GoogleAPI {
    private final OAuthGoogle oAuthGoogle;
    private static String googlePeopleAPIUrl = "https://people.googleapis.com/v1/people/me";

    public User getUser() {
        if(null == oAuthGoogle.getClientId()) {
            throw new OAuthAuthorizationException("OAuth authorization error: empty client_id.");
        }
        if(null == oAuthGoogle.getClientSecret()) {
            throw new OAuthAuthorizationException("OAuth authorization error: empty client_secret.");
        }

        String url = googlePeopleAPIUrl + "?" + "personFields=emailAddresses,names";
        HttpHeaders header = new HttpHeaders();
        RestTemplate template = new RestTemplate();

        header.setBearerAuth(oAuthGoogle.getAccessToken());

        RequestEntity<Void> resourceRequest = RequestEntity.get(url).headers(header).build();
        ResponseEntity<GoogleResource> response = template.exchange(resourceRequest, GoogleResource.class);
//        logger.info(response.getBody());
        GoogleResource resource = response.getBody();
        GoogleResourceElementNames names = resource.getNames().get(0);
        GoogleResourceElementEmailAddress email = resource.getEmailAddresses().get(0);
        return new User(names.getGivenName(), names.getFamilyName(), (byte)1, email.getValue(), names.getMetadata().getSource().getId(), Set.of(new Role("USER")));
    }
}
