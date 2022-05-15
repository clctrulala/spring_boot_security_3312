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

	@GetMapping("{id}")
	public String getUser(@PathVariable Long id, ModelMap model, Authentication auth) {
		if(auth.getAuthorities().contains(Role.ADMIN.giveAuthority())) {
			model.addAttribute("admin_panel",true);
			model.addAttribute("user", userService.getUser(id));
		} else {
			getUser(model, auth);
		}
		return "user";
	}

	@GetMapping
	public String getUser(ModelMap model, Authentication auth) {

		if(auth.getAuthorities().contains(Role.ADMIN.giveAuthority())) {
			model.addAttribute("admin_panel",true);
		}
		model.addAttribute("user", detailsService.loadUserByUsername(auth.getName()));
		return "user";
	}
}