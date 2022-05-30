package spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.boot_security.demo.model.User;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
	private final UserDetailsService detailsService;

	@Autowired
	public UserController(UserDetailsService detailsService) {
		this.detailsService = detailsService;
	}

	@GetMapping
	public String getUser(ModelMap model, Authentication auth) {
		User user = (User)detailsService.loadUserByUsername( auth.getName());

		model.addAttribute("current_user", user);
		model.addAttribute("users", List.of(user));
		return "user";
	}
}