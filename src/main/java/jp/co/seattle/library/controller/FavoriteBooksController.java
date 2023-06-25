package jp.co.seattle.library.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.service.BooksService;

/**
 * Handles requests for the application home page.
 */
@Controller // APIの入り口
public class FavoriteBooksController {
	final static Logger logger = LoggerFactory.getLogger(FavoriteBooksController.class);

	@Autowired
	private BooksService booksService;

	/**
	 * Homeボタンからホーム画面に戻るページ
	 * 
	 * @param model
	 * @return
	 */
	
	/*
	 * お気に入りの書籍だけを取得
	 */
	@RequestMapping(value = "/favBook", method = RequestMethod.GET)
	public String favBook(Model model) {
		List<BookInfo> getedFavoriteList = booksService.getFavBook();
		
		if(getedFavoriteList.isEmpty()) {
			model.addAttribute("resultMessage", "データが存在しません");
		}else {
			model.addAttribute("bookList", getedFavoriteList);
		}
		return "favoriteBook";
	}
	
	/*
	 * お気に入りボタンを押した際、お気に入り画面を表示し直す
	 */
	@RequestMapping(value = "/fav", method = RequestMethod.GET)
	public String favoriteView(@RequestParam("bookId") int bookId,Model model) {
		booksService.getFavorite(bookId);
		return "redirect:/favBook";
	}
	
	/*
	 * お気に入り書籍だけを検索
	 */
	@RequestMapping(value = "/favSearch", method = RequestMethod.GET)
	public String favBookSearch(@RequestParam("search") String search, Model model) {
		List<BookInfo> getedSearchList = booksService.getFavSearchBook(search);
		
		if(getedSearchList.isEmpty()) {
			model.addAttribute("resultMessage", "データが存在しません");
		}else {
			model.addAttribute("bookList", getedSearchList);
		}
		return "favoriteBook";
	}
	
	/*
	 * お気に入り解除ボタンを押されたものを取得
	 */
	@RequestMapping(value = "/unfav", method = RequestMethod.GET)
	public String unlike(@RequestParam("bookId") int bookId, Model model) {
	booksService.deleteFavBook(bookId);
	return "redirect:/favBook";
	}
}
