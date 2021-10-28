package jp.ac.hcs.s3a108.user;

import lombok.Data;

/**
 * 
 * 一件分のユーザ情報
 * 各項目のデータ仕様も合わせて管理する
 */

@Data
public class UserData {
	
	/**
	 * ユーザID(メールアドレス)
	 * 主キー、必須入力、メールアドレス形式
	 */
	private String user_id;
	
	/**
	 * パスワード
	 * 必須入力、長さ4から100桁まで、半角英文字のみ
	 */
	
	private String password;
	
	/**
	 * アカウント有効性
	 * -有効：true
	 * -無効：false
	 * ユーザ作成時はtrue固定
	 */
	
	private boolean enabled;
	
	
	/**
	 * ユーザ名
	 * 必須入力
	 */
	
	private String user_name;
	
	/**
	 * ダークモード
	 * -ON：true
	 * -OFF：false
	 * ユーザ作成時はfalse固定
	 * 
	 */
	
	private boolean darkmode;
	
	/**
	 * 権限
	 * -管理：ROLE_ADMIN
	 * -一般：ROLE_GENERAL
	 * 必須入力
	 */
	
	private Role role;

}

/**
 * 権限のEnumクラス
 */
enum Role{
	ADMIN("ROLE_ADMIN","管理者"),
	GENERAL("ROLE_GENERAL","一般");
	/** 権限id*/
	private String id;
	
	/** 値*/
	private String value;
	
	/** コンストラクタ*/
	Role(String id,String value){
		this.id = id;
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public String getId() {
		return this.id;
	}
	
	/**
	 * IDから合致したPriority型を返却する
	 * @param id
	 * @return Role
	 */
	
	public static Role getRole(String id) {
		for (Role role : values()) {
			if(role.getId().equals(id)) {
				return role;
			}
		}
		throw new IllegalArgumentException("指定されたIDのRoleが存在しません");
	}
}
