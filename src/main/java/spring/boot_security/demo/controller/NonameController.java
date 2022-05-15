package spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.boot_security.demo.util.Role;

@Controller
@RequestMapping("/")
public class NonameController {

    @GetMapping
    public String startPage(Model model, Authentication auth){

        if(null != auth && auth.getAuthorities().contains(Role.ADMIN.giveAuthority())) {
            model.addAttribute("admin_panel",true);
        }
        model.addAttribute("user", auth);
        return "index";
    }
}
