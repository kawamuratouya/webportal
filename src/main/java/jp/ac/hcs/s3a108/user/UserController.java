package jp.ac.hcs.s3a108.user;

import java.security.Principal;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 登録したユーザ一覧を取得する。
	 * 
	 * @param principal ログイン情報
	 * @param model
	 * @return ユーザ一覧画面
	 */
	
	//正規表現
	static final String JUDGE = "^[a-zA-Z0-9]+$";
	
	@GetMapping("/user/list")
	public String getUser(Model model) {
		//userの情報取得
		UserEntity userEntity = userService.getTask();
		
		
		model.addAttribute("userEntity",userEntity);
		
		return "user/userList";
	}
	
	/**
	 * ユーザ登録画面（管理者）を表示する
	 * @param form 登録時の入力チェック用UserForm
	 * @param model
	 * @return ユーザ登録画面
	 */
	@GetMapping("/user/insert")
	public String setUserInsert(UserForm form,Model model) {
		return "user/insert";
	}
	
	/**
	 * 一件分のユーザ情報をデータベースに登録する
	 * @param form 登録するユーザ情報（パスワードは平文）
	 * @param bindingResult データバインド実施結果
	 * @param principal ログイン情報
	 * @model model
	 * @return ユーザ一覧画面
	 */
	@PostMapping("/user/insert")
	public String setUser(@ModelAttribute @Validated UserForm form,BindingResult bindingResult,
			Principal principal,Model model) {
		
		//userform→userdataメソッドに置換する
		UserData data = userService.chengeMethod(form);
		
		//チェック時問題がある場合戻す
		if (bindingResult.hasErrors()) {
			return setUserInsert(form,model);
		}else {
			boolean isJudge = userService.insertUser(data);
			if (isJudge) {
				log.info("UserAccount Registeird Sucsess");
			}else {
				log.info("UserAccount Registeird Failed");	
			}
			
			System.out.println("isnerted");
			return getUser(model);
			
		}
		
		
	}
	
	/**
	 * ユーザ詳細情報画面を表示する
	 * @param user_id 検索するユーザID
	 * @param principal ログイン情報
	 * @param model
	 * @return ユーザ詳細情報画面
	 */
	
	@GetMapping("/user/detail/{id}")
	public String getUserData(@PathVariable ("id") String user_id,Principal principal,Model model) {
		
		//正規表現処理
		Pattern p = Pattern.compile(JUDGE);
		String returns = null;
		
		//必須チェック
		if (user_id == null) {
			log.info("NO DATE!!");
			returns =  getUser(model);
		}else if ((p.matcher(user_id).find())) { 
			log.info("Tampering to your user_id!!");
			returns =  getUser(model);
		}else {
			UserData data = userService.getUserOne(user_id);
			if (data == null) {
				log.info("Your user_id is Invalid !!");
				returns =  getUser(model);
			}else {
				model.addAttribute("userData",data);
				returns = "user/detail";
			}
		}
		
		return returns;
	}
	
	/**
	 * ユーザ情報を削除する
	 * @param user_id ユーザID情報
	 * @param model
	 * @return ユーザ一覧画面 
	 */
	
	@RequestMapping("/user/delete/{user_id}")
	public String setDeleteUser(@PathVariable ("user_id") String user_id,Model model) {
		System.out.println("go");
		boolean isJudge = userService.deleteUserOne(user_id);
		if (isJudge) {
			log.info("UserAccount Deleted Sucsess");
		}else {
			log.info("UserAccount Deleted Failed");	
		}
		
		return getUser(model);
	}
}
