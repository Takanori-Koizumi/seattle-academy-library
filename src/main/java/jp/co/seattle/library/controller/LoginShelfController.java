package jp.co.seattle.library.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.UserInfo;
import jp.co.seattle.library.service.UsersService;

/**
 * ログインコントローラー
 */
@Controller /** APIの入り口 */
public class LoginShelfController {
	final static Logger logger = LoggerFactory.getLogger(LoginShelfController.class);

	@Autowired
	private UsersService usersService;

//	@RequestMapping(value = "/bookShelf", method = RequestMethod.GET)
//	public String first(Model model) {
//		return "login"; // jspファイル名
//	}

	/**
	 * ログイン処理
	 *
	 * @param email    メールアドレス
	 * @param password パスワード
	 * @param shelfPassword 本棚用パスワード
	 * @param model
	 * @return ホーム画面に遷移
	 */
	@RequestMapping(value = "/loginShelf", method = RequestMethod.POST)
	public String login(@RequestParam("email") String email, @RequestParam("shelfPassword") String shelfPassword, Model model) {

		// メアドとパスワードに一致するユーザー取得
		UserInfo selectedUserInfo = usersService.selectUserShelf(email, shelfPassword);
			System.out.println(selectedUserInfo);
		// ユーザーが存在すればログイン、存在しなければエラー(タスク２)
		if (Objects.nonNull(selectedUserInfo)) {
			return "redirect:/bookShelf";
		} else {
			model.addAttribute("errorMessage", "メールアドレスとパスワードが一致しません。");
			return "shelfLogin";
		}

	}
}