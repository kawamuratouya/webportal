package jp.ac.hcs.s3a108.zipcode;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ZipCodeController {
	@Autowired
	private ZipCodeService zipCodeService;
	
	@PostMapping("/zip")
	public String getZipCode(@RequestParam("zipcode") String zipcode,
			Principal principal, Model model) {
		
		log.info("meesage");
		ZipCodeEntity zipCodeEntity = zipCodeService.getZip(zipcode);
		model.addAttribute("zipCodeEntity",zipCodeEntity);
		
		return "zipcode/zipcode";
	}

}
