package jp.ac.hcs.s3a108.gourmet;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
/**
 * 
 * 店舗検索結果の店舗情報
 * 各項目のデータ仕様については、APIの仕様を参照とする
 * 検索内容で複数の結果が紐づく事もあるため、リスト構造となっている
 * - http://webservice.recruit.co.jp/hotpepper/gourmet/v1/
 *
 */
@Data
public class ShopEntity {
	/** 店舗情報のリスト*/
	private List<ShopData> shop = new ArrayList<ShopData>();
}
