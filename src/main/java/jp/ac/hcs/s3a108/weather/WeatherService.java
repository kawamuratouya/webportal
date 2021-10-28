package jp.ac.hcs.s3a108.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional
@Service
public class WeatherService {
	
	@Autowired
	RestTemplate restTemplate;
	

	private static final String URL = "https://weather.tsukumijima.net/api/forecast?city={cityCode}";

	public WeatherEntity getWeather(String cityCode) {
		
		// APIへアクセスして、結果を取得
		String json = restTemplate.getForObject(URL, String.class, cityCode);
		
		// エンティティクラスを生成
		WeatherEntity weatherEntity = new WeatherEntity();
		
		// jsonクラスへの変換失敗のため、例外処理
		try {
				//変換クラスを生成し、文字列からjsonクラスへ変換する(例外の可能性あり)
				ObjectMapper mapper = new ObjectMapper();
				JsonNode node = mapper.readTree(json);
				for(JsonNode forecast : node.get("forecasts")) {
					//データクラスの生成(result1件分)
					WeatherData data = new WeatherData();
					data.setDateLabel(forecast.get("dateLabel").asText());
					data.setTelop(forecast.get("telop").asText());
					
					// 可変長配列の末尾に追加
					weatherEntity.getForecasts().add(data);
				}
				WeatherData weatherData=new WeatherData();
				
				  weatherData.setHeadlineText(node.get("description").get("headlineText").asText());
				  weatherData.setBodyText(node.get("description").get("bodyText").asText());
				  weatherData.setText(node.get("description").get("text").asText());
				
				//可変長配列の末尾に追加
				weatherEntity.getDescription().add(weatherData);
				
		} catch (Exception e) {
			// 例外発生時は、エラーメッセージの詳細を標準エラー出力
			e.printStackTrace();
		}
		
		return weatherEntity;
	}

}
