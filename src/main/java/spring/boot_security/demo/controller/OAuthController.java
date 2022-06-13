package spring.boot_security.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.boot_security.demo.security.OAuthGoogle;
import spring.boot_security.demo.service.GoogleAPI;
import spring.boot_security.demo.service.OAuthService;

import java.util.Optional;

@Controller
@RequestMapping("login/oauth")
@AllArgsConstructor
public class OAuthController {
    private final OAuthGoogle oAuthGoogle;
    private final OAuthService oAuthService;
    private final GoogleAPI googleAPI;

    @GetMapping
    public String oAuth(Optional<String> code) {
        if(code.isPresent()) {
            oAuthGoogle.authorize(code.get());
            oAuthService.checkUser(googleAPI.getUser());
            return "redirect:/user";
        }
        return oAuthGoogle.redirectURL();
    }
}
