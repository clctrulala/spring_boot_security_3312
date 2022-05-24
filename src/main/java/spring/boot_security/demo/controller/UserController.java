package spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.boot_security.demo.model.Role;

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
		UserDetails userDetails = detailsService.loadUserByUsername( auth.getName());

		if(userDetails.getAuthorities().contains(new Role(Role.ADMIN))){
			model.addAttribute("comeback", true);
		}
		model.addAttribute("current_user", userDetails);
		model.addAttribute("users", List.of(userDetails));
		return "users_list";
	}
}