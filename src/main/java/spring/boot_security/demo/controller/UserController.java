package spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.boot_security.demo.service.UserService;
import spring.boot_security.demo.util.Role;

@Controller
@RequestMapping("/user")
public class UserController {
	private final UserDetailsService detailsService;
	private final UserService userService;

	@Autowired
	public UserController(UserDetailsService detailsService, UserService userService) {
		this.detailsService = detailsService;
		this.userService = userService;
	}

	@GetMapping
	public String getUser(ModelMap model, Authentication auth) {
		model.addAttribute("admin", detailsService.loadUserByUsername(auth.getName()));
		return "user_list";
	}
}