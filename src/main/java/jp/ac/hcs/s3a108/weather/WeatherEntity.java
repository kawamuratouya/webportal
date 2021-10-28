package jp.ac.hcs.s3a108.weather;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class WeatherEntity {
	private String title;
	
	private List<WeatherData> description = new ArrayList<WeatherData>();
	
	private List<WeatherData> forecasts = new ArrayList<WeatherData> ();

}
