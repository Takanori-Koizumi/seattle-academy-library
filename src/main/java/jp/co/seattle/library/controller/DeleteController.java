package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.commonutil.BookUtil;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.ThumbnailService;

public class DeleteController {
	final static Logger logger = LoggerFactory.getLogger(EditBookController.class);

	@Autowired
	private BooksService booksService;
	@Autowired
	private ThumbnailService thumbnailService;
	@Autowired
	private BookUtil bookUtil;

	@RequestMapping(value = "/deleteBook", method = RequestMethod.POST)
	public String deletebook(Locale locale, @RequestParam("bookId")int bookId,Model model) {
		
		//書籍情報を削除する
		booksService.deleteBook(bookId);
		
		//一覧画面に遷移する
		return "redirect:/home";
	}
}
