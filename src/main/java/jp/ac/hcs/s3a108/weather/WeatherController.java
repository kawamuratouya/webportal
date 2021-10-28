package jp.ac.hcs.s3a108.weather;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WeatherController {
	@Autowired
	private WeatherService weatherService;
	
	@PostMapping("/weather")
	public String postWeather(Principal principal, Model model) {
		
		String citycode = "016010";
		
		WeatherEntity weatherEntity = weatherService.getWeather(citycode);
		model.addAttribute("weatherEntity", weatherEntity);
		
		return "weather/weather";
	}
	
	

}
