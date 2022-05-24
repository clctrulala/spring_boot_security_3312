package spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginLogoutController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("logout")
    public String logout() {
        return "redirect:/login";
    }
}
