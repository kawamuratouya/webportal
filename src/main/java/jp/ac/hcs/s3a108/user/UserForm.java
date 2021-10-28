package jp.ac.hcs.s3a108.user;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * 
 * フロント-バックでユーザ情報をやり取りする
 * 各項目のデータ仕様はUserEntityを参照
 */

@Data
public class UserForm {
	
	/** ユーザID（メールアドレス）*/
	@NotBlank(message = "{require_check}")
	@Email(message = "{email_check}")
	private String user_id;
	
	/** パスワード*/
	@NotBlank(message = "{require_check}")
	@Length(min = 4,max = 100,message= "{length_check}")
	@Pattern(regexp = "^[a-zA-Z0-9]+$",message = "{pattern_check}")
	private String password;
	
	/** ユーザ名*/
	@NotBlank(message = "{require_check}")
	private String user_name;
	
	/** ダークモードフラグ　妥当性が分からない（false_checkを利用する。）*/
	@AssertFalse(message = "{false_check}")
	private boolean darkmode;
	
	/** 権限*/
	@NotBlank(message = "{require_check}")
	private String role;

}
