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

import jp.co.seattle.library.commonutil.BookUtil;
import jp.co.seattle.library.dto.UserInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.UsersService;

public class ResetPassController {
	final static Logger logger = LoggerFactory.getLogger(ResetPassController.class);

	@Autowired
	private BooksService booksService;

	@Autowired
	private BookUtil bookUtil;

	@RequestMapping(value = "/newPass", method = RequestMethod.GET)
	public String transitionEdit(Locale locale, int bookId, Model model) {
		logger.info("Welcome EditBooks.java! The client locale is {}.", locale);
		
		return "resetpass";
	}

	@Controller // APIの入り口
	public class AccountController {
		final Logger logger = LoggerFactory.getLogger(LoginController.class);

		@Autowired
		private UsersService usersService;

		@RequestMapping(value = "/newPass", method = RequestMethod.GET) // value＝actionで指定したパラメータ
		public String createAccount(Model model) {
			return "resetpass";
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
		@Transactional
		@RequestMapping(value = "/createAccount", method = RequestMethod.POST)
		public String createAccount(Locale locale, @RequestParam("email") String email,
				@RequestParam("password") String password, @RequestParam("passwordForCheck") String passwordForCheck,
				Model model) {
			// デバッグ用ログ
			logger.info("Welcome createAccount! The client locale is {}.", locale);

			// バリデーションチェック、パスワード一致チェック（タスク１）
			if (password.matches("^[A-Za-z0-9]+$") && password.length() >= 8) {
				if (password.equals(passwordForCheck)) {
					UserInfo userInfo = new UserInfo();
					userInfo.setEmail(email);
					userInfo.setPassword(password);
					usersService.registUser(userInfo);
					return "redirect:/login";
				} else {

					model.addAttribute("errorMessage", "パスワードが一致しません。");
					return "resetpass";
				}

			} else {
				model.addAttribute("errorMessage", "パスワードは8文字以上かつ半角英数字に設定してください。");
				return "resetpass";
			}

		}

	}
}
