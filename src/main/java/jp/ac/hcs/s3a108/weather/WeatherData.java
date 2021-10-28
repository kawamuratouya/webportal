package jp.ac.hcs.s3a108.weather;

import lombok.Data;

@Data
public class WeatherData {
	private String dateLabel;
	
	private String telop;
	
	private String description;
	
	private String headlineText;
	private String bodyText;
	private String text;

}
