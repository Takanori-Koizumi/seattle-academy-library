package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 * 書籍サービス
 * 
 * booksテーブルに関する処理を実装する
 */
@Service
public class BooksService {
	final static Logger logger = LoggerFactory.getLogger(BooksService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 書籍リストを取得する
	 *
	 * @return 書籍リスト
	 */
	public List<BookInfo> getBookList() {

		// TODO 書籍名の昇順で書籍情報を取得するようにSQLを修正（タスク３）
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY title asc;",
				new BookInfoRowMapper());

		return getedBookList;
	}

	/*
	 * 検索に引っかかったものを取得する
	 */
	public List<BookInfo> getSearchBook(String search) {
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books WHERE title like concat('%',?,'%') or tag like concat('%',?,'%') ORDER BY title asc;",
				new BookInfoRowMapper(), search, search);

		return getedBookList;
	}
	
	/*
	 *お気に入り書籍の中で検索に引っかかったものを取得する
	 */
	public List<BookInfo> getFavSearchBook(String search) {
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books WHERE favorite='like' AND title like concat('%',?,'%') OR tag like concat('%',?,'%') ORDER BY title asc;",
				new BookInfoRowMapper(), search, search);

		return getedBookList;
	}
	
	/*
	 * お気に入り登録されているものを取得する
	 */
	public List<BookInfo> getFavBook() {
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books WHERE favorite='like' ORDER BY title asc;",
				new BookInfoRowMapper());

		return getedBookList;
	}
	
	/**
	 * 書籍IDに紐づく書籍詳細情報を取得する
	 *
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */
	public BookDetailsInfo getBookInfo(int bookId) {
		String sql = "SELECT id, title, author, publisher, publish_date, isbn, description, tag, thumbnail_url, thumbnail_name, favorite, status FROM books WHERE books.id = ? ORDER BY title ASC;";

		BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper(), bookId);

		return bookDetailsInfo;
	}
	
	/*
	 * お気に入りを登録する
	 */
	public void getFavorite(int bookId) {
		String sql = "UPDATE books SET favorite='like' WHERE books.id = ?;";

		jdbcTemplate.update(sql, bookId);
	}

	/*
	 * お気に入りを削除する
	 */
	public void deleteFavBook(int bookId) {
		// TODO 対象の書籍を削除するようにSQLを修正（タスク6）
		String sql = "UPDATE books SET favorite='unlike' WHERE books.id = ?;";
		jdbcTemplate.update(sql, bookId);
	}
	
	/*
	 * 本棚に登録する
	 */
	public void getRegistShelfBook(int bookId) {
		String sql = "UPDATE books SET shelf='register' WHERE books.id = ?;";

		jdbcTemplate.update(sql, bookId);
	}
	
	/*
	 * 本棚から削除する
	 */
	public void getDeleteShelfBook(int bookId) {
		String sql = "UPDATE books SET shelf='delete' WHERE books.id = ?;";

		jdbcTemplate.update(sql, bookId);
	}
	
	/**
	 * 書籍を登録する
	 *
	 * @param bookInfo 書籍情報
	 * @return bookId 書籍ID
	 */
	public int registBook(BookDetailsInfo bookInfo) {
		// TODO 取得した書籍情報を登録し、その書籍IDを返却するようにSQLを修正（タスク４）
		String sql = "INSERT INTO books(title, author, publisher, publish_date, thumbnail_name, thumbnail_url, isbn, description, tag, reg_date, upd_date) VALUES(?,?,?,?,?,?,?,?,?, now(), now()) RETURNING id;";

		int bookId = jdbcTemplate.queryForObject(sql, int.class, bookInfo.getTitle(), bookInfo.getAuthor(),
				bookInfo.getPublisher(), bookInfo.getPublishDate(), bookInfo.getThumbnailName(),
				bookInfo.getThumbnailUrl(), bookInfo.getIsbn(), bookInfo.getDescription(), bookInfo.getTag());
		return bookId;
	}

	/**
	 * 書籍を削除する
	 * 
	 * @param bookId 書籍ID
	 */
	public void deleteBook(int bookId) {
		// TODO 対象の書籍を削除するようにSQLを修正（タスク6）
		String sql = "DELETE FROM books WHERE books.id=?;";
		jdbcTemplate.update(sql, bookId);
	}
	
	
	/**
	 * 書籍情報を更新する
	 * 
	 * @param bookInfo
	 */
	public void updateBook(BookDetailsInfo bookInfo) {
		String sql;
		if (bookInfo.getThumbnailUrl() == null) {
			// TODO 取得した書籍情報を更新するようにSQLを修正（タスク５）
			sql = "UPDATE books SET title=?, author=?, publisher=?, publish_date=?, isbn=?, description=?, tag=?, upd_date=now() WHERE id=?;";
			jdbcTemplate.update(sql, bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getPublisher(),
					bookInfo.getPublishDate(), bookInfo.getIsbn(), bookInfo.getDescription(), bookInfo.getTag(), bookInfo.getBookId());
		} else {
			// TODO 取得した書籍情報を更新するようにSQLを修正（タスク５）
			sql = "UPDATE books SET title=?, author=?, publisher=?, publish_date=?, thumbnail_name=?, thumbnail_url=?, isbn=?, description=?, tag=?, upd_date=now() WHERE id=?;";
			jdbcTemplate.update(sql, bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getPublisher(),
					bookInfo.getPublishDate(), bookInfo.getThumbnailName(), bookInfo.getThumbnailUrl(),
					bookInfo.getIsbn(), bookInfo.getDescription(), bookInfo.getTag(), bookInfo.getBookId());
		}
	}
	
	/*
	 * 本棚に登録されているものを取得する
	 */
	public List<BookInfo> getShelfBook() {
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books WHERE shelf='register' ORDER BY title asc;",
				new BookInfoRowMapper());

		return getedBookList;
	}
	
	/*
	 * 選択されたタグの書籍を取得
	 */
	public List<BookInfo> select_check(String getText) {
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books WHERE tag=? ORDER BY title asc;",
				new BookInfoRowMapper(), getText);

		return getedBookList;
	}
	
	/*
	 * 読了のチェックをDBに登録
	 */
	public void state_check(String value, int bookId) {
		   String sql = "UPDATE books SET status = ? WHERE books.id = ?;";
		   jdbcTemplate.update(sql, value, bookId);
		  }
}
