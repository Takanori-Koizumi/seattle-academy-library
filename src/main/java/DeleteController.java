import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.controller.EditBookController;
import jp.co.seattle.library.service.BooksService;


public class DeleteController {
	final static Logger logger = LoggerFactory.getLogger(EditBookController.class);

	@Autowired
	private BooksService booksService;
	
	@RequestMapping(value = "/deleteBook", method = RequestMethod.POST)
	public String deletebook(Locale locale, @RequestParam("boolId")int bookId, Model model) {
		
		booksService.deleteBook(bookId);
		
		return "redirect:/home";
	}

}
