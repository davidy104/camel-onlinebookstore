package co.nz.onlinebookstore.service;

import java.util.Set;

import co.nz.onlinebookstore.data.BookDto;

public interface BookService {
	Long createBook(BookDto book) throws Exception;
	void updateBook(Long bookId, BookDto book) throws Exception;
	void deleteBook(Long bookId) throws Exception;
	Set<BookDto> getAllBooks() throws Exception;
	BookDto getBookById(Long bookId) throws Exception;
	BookDto getBookByIsbn(String isbn) throws Exception;
	BookDto getBookByName(String bookName) throws Exception;
}
