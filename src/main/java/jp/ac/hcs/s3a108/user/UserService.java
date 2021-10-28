package jp.ac.hcs.s3a108.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ユーザ情報を配列に入れる操作を行う。

 */
@Transactional
@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	/**
	 * 登録しているユーザ情報を抽出する。
	 * @return userEntity
	 */

	public UserEntity getTask() {
		
		//ユーザーエンティティの作成
		UserEntity userEntity;
		try {
			userEntity = userRepository.selectAll();
			
		}catch(DataAccessException e){
			e.printStackTrace();
			userEntity = null;
		}
		
		return userEntity;
	}
	
	/**
	 * ユーザ情報を一件追加する
	 * @param userData 追加するユーザ情報（パスワードは平文）
	 * @return 処理結果（成功：true,失敗：false）
	 */
	
	public boolean insertUser(UserData userData) {
		int rowNumber;
		try {
			rowNumber = userRepository.insertOne(userData);
		}catch (DataAccessException e) {
			e.printStackTrace();
			rowNumber = 0;
		}
		return rowNumber > 0;
	}
	
	/**
	 * 入力項目をUserDataへ変換する
	 * （このメソッドは入力チェックを実施したうえで呼び出すこと）
	 * @param form 入力データ
	 * @return UserData
	 * 
	 */
	
	UserData chengeMethod(UserForm form) {
		UserData data = new UserData();
		data.setUser_id(form.getUser_id());
		data.setPassword(form.getPassword());
		data.setUser_name(form.getUser_name());
		data.setDarkmode(form.isDarkmode());
		
		//ロールの設定
		String role = form.getRole();
		data.setRole(Role.getRole(role));
		
		//初期値は有効設定
		data.setEnabled(true);
		return data;
	}
	
	
	/**
	 * ユーザ情報を一件抽出する。
	 * @param user_id ユーザID
	 * @return userData 
	 * 
	 */
	
	public UserData getUserOne(String user_id) {
		//ユーザーエンティティの作成
		UserData userData;
		try {
			userData = userRepository.selectOne(user_id);
			
		}catch(DataAccessException e){
			e.printStackTrace();
			userData = null;
		}
		
		return userData;
	}
	
	/**
	 * ユーザ情報を一件削除する
	 * 
	 * @return 処理結果（成功：true,失敗：false）
	 */
	
	public boolean deleteUserOne(String user_id) {
		int rowNumber;
		try {
			rowNumber = userRepository.deleteOne(user_id);
		}catch (DataAccessException e) {
			e.printStackTrace();
			rowNumber = 0;
		}
		return rowNumber > 0;
	}
}
