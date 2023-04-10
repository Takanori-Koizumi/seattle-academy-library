package jp.co.seattle.library.controller;

import java.util.Locale;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.UserInfo;
import jp.co.seattle.library.service.UsersService;

/**
 * アカウント作成コントローラー
 */
@Controller // APIの入り口
public class AccountController {
	final static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/newAccount", method = RequestMethod.GET) // value＝actionで指定したパラメータ
	public String createAccount(Model model) {
		return "createAccount";
	}

	/**
	 * 新規アカウント作成
	 *
	 * @param email            メールアドレス
	 * @param password         パスワード
	 * @param passwordForCheck 確認用パスワード
	 * @param model
	 * @return ホーム画面に遷移
	 */

	//email
	public boolean validateEmail(String em) {
		String pattern = "^([a-zA-Z0-9])+([a-zA-Z0-9\\._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9\\._-]+)+";
		Pattern p = Pattern.compile(pattern);
		if (p.matcher(em).find()) {
			return true;
		} else {
			return false;
		}
	}

	//password
	public static boolean validatePassword(String ch, String password) {
		boolean check = false;
		if (password.length() >= 8) {
			Pattern p1 = Pattern.compile(ch); // 正規表現パターンの読み込み
			java.util.regex.Matcher m1 = p1.matcher(password); // パターンと検査対象文字列の照合
			check = m1.matches(); // 照合結果をtrueかfalseで取得
			if (check) {
				System.out.println("パスワードが正しい");
				return check;
			} else {
				System.out.println("パスワードが正しくない");
				return check;
			}
		} else {
			System.out.println("パスワードが正しくない");
			return check;
		}
	}

	@Transactional
	@RequestMapping(value = "/createAccount", method = RequestMethod.POST)
	public String createAccount(Locale locale, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("passwordForCheck") String passwordForCheck,
			Model model) {
		// デバッグ用ログ
		logger.info("Welcome createAccount! The client locale is {}.", locale);
		
		//start
		boolean emailResult = validateEmail(email);
		if (!emailResult) {
			model.addAttribute("errorMessage", "正しいemailを入力してください");
		}
		
		String regex_AlphaNum = "^[A-Za-z0-9]+$";
		boolean validateResult = validatePassword(regex_AlphaNum, password);
		if (!validateResult) {
			model.addAttribute("errorMessage", "パスワードが８文字以上かつ半角英数字ではありません");
			return "createAccount";
		}
		if (password.equals(passwordForCheck)) {
			System.out.println("OK");
			UserInfo userInfo = new UserInfo();
			userInfo.setEmail(email);
			userInfo.setPassword(password);
			usersService.registUser(userInfo);
			return "redirect:/login";
		} else {
			model.addAttribute("errorMessage", "パスワードが一致しません");
			return "createAccount";
		}

		// パラメータで受け取ったアカウント情報をDtoに格納する。
	}

}
