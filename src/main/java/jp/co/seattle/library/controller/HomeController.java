package jp.co.seattle.library.controller;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.service.BooksService;

/**
 * Handles requests for the application home page.
 */
@Controller // APIの入り口
public class HomeController {
	final static Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private BooksService booksService;

	/**
	 * Homeボタンからホーム画面に戻るページ
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String transitionHome(Model model) {
		//書籍の一覧情報を取得（タスク３）
		List<BookInfo> getedBookList = booksService.getBookList();
		
		if (Objects. isNull(getedBookList)) {
			model.addAttribute("resultMessage", "データが存在しません");
		}else{
			model.addAttribute("bookList", getedBookList);
		}
		return "home";
	}
	/*
	 * 検索に入力されたものを表示
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String bookSearch(@RequestParam("search") String search, Model model) {
		List<BookInfo> getedSearchList = booksService.getSearchBook(search);
		
		if(getedSearchList.isEmpty()) {
			model.addAttribute("resultMessage", "データが存在しません");
		}else {
			model.addAttribute("bookList", getedSearchList);
		}
		return "home";
	}
	
	/*
	 * お気に入りボタンを押されたものを取得
	 */
	@RequestMapping(value = "/favorite", method = RequestMethod.GET)
	public String favorite(@RequestParam("bookId") int bookId, Model model) {
	booksService.getFavorite(bookId);
	return "redirect:/home";
	}
	
	/*
	 * お気に入り解除ボタンを押されたものを取得
	 */
	@RequestMapping(value = "/unlike", method = RequestMethod.GET)
	public String unlike(@RequestParam("bookId") int bookId, Model model) {
	booksService.deleteFavBook(bookId);
	return "redirect:/home";
	}
	
	/*
	 * 本棚のログイン画面を表示
	 */
	@RequestMapping(value = "/loginBookShelf", method = RequestMethod.GET)
	public String first(Model model) {
		return "shelfLogin"; 
	}
	
	/*
	 * チェックボタンで選択肢、本棚に追加を押されたものを取得
	 */
	@RequestMapping(value = "/addShelf", method = RequestMethod.POST)
	public String addShelfBook(@RequestParam("bookId") int[] arr, Model model) {
		
		for(int i=0;i<arr.length;i++) {
			int id = arr[i];
			booksService.getRegistShelfBook(id);
		}

		return "redirect:/home";
	}
	
	
	/*
	 * チェックボタンで選択肢、本棚に追加を押されたものを取得
	 */
	@RequestMapping(value = "/readStatus", method = RequestMethod.POST)
	  public String Status(@RequestParam("value") String value, @RequestParam("bookId") int bookId, Model model) {
	  booksService.state_check(value ,bookId);
	  return "redirect:/home";
	  }
	
	/*
	 * チェックボタンで選択肢、本棚に追加を押されたものを取得
	 */
	@RequestMapping(value = "/selectTag", method = RequestMethod.POST)
	  public String Select(@RequestParam("getText") String getText,  Model model, RedirectAttributes redirectAttributes) {
		List<BookInfo> getedTagList = booksService.select_check(getText);
		
		if(getedTagList.isEmpty()) {
			redirectAttributes.addFlashAttribute("resultMessage", "データが存在しません");
		}else {
			
			redirectAttributes.addFlashAttribute("bookList", getedTagList);
		}
		return "redirect:/tagHome";
	  }
	
	@RequestMapping(value = "/tagHome", method = RequestMethod.GET)
	public String tagHome(Model model) {
		
		return "home";
	}
}
