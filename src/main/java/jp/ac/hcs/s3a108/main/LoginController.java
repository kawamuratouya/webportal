package jp.ac.hcs.s3a108.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@GetMapping("/login")
	public String getLogin(Model model) {
		return "login";
		
	}

}
