package spring.boot_security.demo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import spring.boot_security.demo.types.GoogleAccessTokenResponse;
import spring.boot_security.demo.types.OAuthAuthorizationException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.StringJoiner;

@Component
@PropertySource("classpath:application.properties")
public class OAuthGoogle {
    public static String PROMPT_CONSENT = "consent";
    public static String PROMPT_NONE = "none";
    public static String PROMPT_SELECT_ACCOUNT = "select_account";
    public static String SCOPE_PROFILE = "https://www.googleapis.com/auth/userinfo.profile";
    public static String SCOPE_EMAIL = "https://www.googleapis.com/auth/userinfo.email";
    public static String SCOPE_OPENID = "openid";
    public static String SCOPE_AGERANGE = "https://www.googleapis.com/auth/profile.agerange.read";
    private static String googleTokenUrl = "https://oauth2.googleapis.com/token";
    private static String googleAuthorizationUrl = "https://accounts.google.com/o/oauth2/v2/auth";

    @Value("${client_id}")
    private String clientId;
    @Value("${client_secret}")
    private String clientSecret;
    private String scope = SCOPE_PROFILE + " " + SCOPE_EMAIL + " " + SCOPE_OPENID;
    private String state;
    private String redirectURI = "http://localhost:8080/login/oauth";
    private String prompt = PROMPT_CONSENT;
    private boolean includeGrantedScopes = false;
    private String loginHint;
    private String accessToken;

    public OAuthGoogle setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public OAuthGoogle setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public OAuthGoogle setScope(Set<String> scope) {
        this.scope = scope.stream()
                .reduce("", (old, current) -> old + " " + current);
        return this;
    }

    public OAuthGoogle setState(String state) {
        this.state = state;
        return this;
    }

    public OAuthGoogle setRedirectURI(String redirectURI) {
        this.redirectURI = URLEncoder.encode(redirectURI, StandardCharsets.UTF_8);
        return this;
    }

    public OAuthGoogle allowGrantedScopes(boolean allowScopes) {
        this.includeGrantedScopes = allowScopes;
        return this;
    }

    public OAuthGoogle setLoginHint(String emailHint) {
        this.loginHint = emailHint;
        return this;
    }

    public String redirectURL() {
        StringJoiner parameters = new StringJoiner("&")
                .add("client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8))
                .add("access_type=online")
                .add("scope=" + URLEncoder.encode(scope, StandardCharsets.UTF_8))
                .add("include_granted_scopes=" + includeGrantedScopes)
                .add("response_type=code")
                .add("redirect_uri=" + URLEncoder.encode(redirectURI, StandardCharsets.UTF_8))
                .add("prompt=" + URLEncoder.encode(prompt, StandardCharsets.UTF_8));

        if(null != loginHint) {
            parameters.add("login_hint=" + loginHint);
        }
        if(null != state) {
            parameters.add("state=" + state);
        }
        if(null == clientId) {
            throw new OAuthAuthorizationException("OAuth redirect error: empty client_id.");
        }
        return "redirect:" + googleAuthorizationUrl + "?" + parameters;
    }

    public void authorize(String code) {
        RestTemplate template = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        MultiValueMap<String, String> tokenRequest = new LinkedMultiValueMap<>();

        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        tokenRequest.add("client_id", clientId);
        tokenRequest.add("client_secret", clientSecret);
        tokenRequest.add("redirect_uri", redirectURI);
        tokenRequest.add("grant_type", "authorization_code");
        tokenRequest.add("code", code);

        RequestEntity<MultiValueMap<String, String>> request = RequestEntity.post(googleTokenUrl).headers(header).body(tokenRequest);

        if(null == clientId) {
            throw new OAuthAuthorizationException("OAuth authorization error: empty client_id.");
        }
        if(null == clientSecret) {
            throw new OAuthAuthorizationException("OAuth authorization error: empty client_secret.");
        }

        GoogleAccessTokenResponse access = template.postForObject(googleTokenUrl, request, GoogleAccessTokenResponse.class);
        accessToken = access.getAccessToken();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}

