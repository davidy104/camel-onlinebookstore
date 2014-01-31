package co.nz.onlinebookstore.data.predicates;

import co.nz.onlinebookstore.data.QBookModel;

import com.mysema.query.types.Predicate;

public class BookPredicates {
	public static Predicate findByIsbn(final String isbn) {
		QBookModel book = QBookModel.bookModel;
		return book.isbn.eq(isbn);
	}

	public static Predicate findByBookName(final String bookName) {
		QBookModel book = QBookModel.bookModel;
		return book.bookName.eq(bookName);
	}
}
