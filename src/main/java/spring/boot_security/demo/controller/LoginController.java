package spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping
    public String login(@RequestParam(required = false) String error) {
        if(null != error) { return "error"; }
        return "login";
    }
}
