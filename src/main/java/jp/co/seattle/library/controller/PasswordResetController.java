package jp.co.seattle.library.controller;

import java.util.Locale;

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
public class PasswordResetController {
	final static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/resetPass", method = RequestMethod.GET) 
	public String ResetPass(Model model) {
		return "passwordReset";
	}

	
	@Transactional
	
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public String Reset(Locale locale, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("passwordForCheck") String passwordForCheck,
			Model model) {
		logger.info("Welcome resetAccount! The client locale is {}.", locale);
		
		// バリデーションチェック、パスワード一致チェック（タスク１）
		if (password.length() >= 8 && password.matches("^[A-Za-z0-9]+$")) {
			if (password.equals(passwordForCheck)) {
				// パラメータで受け取ったアカウント情報をDtoに格納する。
				UserInfo userInfo = new UserInfo();
				userInfo.setEmail(email);
				userInfo.setPassword(password);
				usersService.resetPass(userInfo);
			} else {
				model.addAttribute("errorMessage", "パスワードが一致しません。");
				return "reset";
			}
		} else {
			model.addAttribute("errorMessage", "パスワードは8文字以上かつ半角英数字に設定してください。");
			return "reset";
		}
		return "redirect:/";
	}

}
