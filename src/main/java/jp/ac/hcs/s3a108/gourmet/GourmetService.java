package jp.ac.hcs.s3a108.gourmet;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 店舗情報を操作する
 * リクルート社のグルメサーチAPIを活用する
 *- http://webservice.recruit.co.jp/hotpepper/gourmet/v1/
 */
@Transactional
@Service
public class GourmetService {
	@Autowired
	RestTemplate restTemplate;
	/** APIキー*/
	private static final String API_KEY = "3f22fd6e3a437e7e";
	/** グルメサーチAPI リクエストURL*/
	private static final String URL = "http://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key={API_KEY}&name={shopname}&large_service_area={large_service_area}&format=json";
	/**
	 * 指定したキーワードに紐づく店舗情報を取得する。
	 * @param keyword 検索
	 * @param large_service_area 地域設定コード
	 * 
	 * @return shopEntity
	 */
	public ShopEntity findGourmet(String keyword,String large_service_area) {
		//APIへアクセスして、結果を取得
		String json = restTemplate.getForObject(
				URL,String.class,API_KEY,keyword,large_service_area);
		System.out.println(json);
		//エンティティクラスを生成
		ShopEntity shopEntity = new ShopEntity();
		//jsonクラスへの変換失敗のため、例外処理
		try {
			//変換クラスを生成し、文字列からjspnクラスへ変換する(例外の可能性あり)
			ObjectMapper mapper=new ObjectMapper();
			JsonNode node=mapper.readTree(json);
			String shops = node.get("results").get("shop").asText();
			//メッセージに何か入力されたらエラー処理を行う。
			if (shops == "null") {
				ShopData shopData=new ShopData();
				shopData.setName("条件に該当する住所は見つかりませんでした。");
				shopEntity.getShop().add(shopData);
			}
			else {
				//resultsパラメータの抽出(配列分取得する)
				for(JsonNode shop:node.get("results").get("shop")) {
					//データクラスの生成(results1件分)
					ShopData shopData=new ShopData();
					shopData.setId(shop.get("id").asText());
					shopData.setName(shop.get("name").asText());
					shopData.setLogo_image(shop.get("logo_image").asText());
					shopData.setName_kana(shop.get("name_kana").asText());
					shopData.setAddress(shop.get("address").asText());
					shopData.setAccess(shop.get("access").asText());
					shopData.setUrl(shop.get("urls").get("pc").asText());
					shopData.setImage(shop.get("photo").get("mobile").get("l").asText());
					//可変長配列の末尾に追加
					shopEntity.getShop().add(shopData);
				}
			}
		}catch(IOException e) {
			//例外発生時は、エラーメッセージの詳細を標準エラー出力
			e.printStackTrace();
		}
		return shopEntity;
	}
}