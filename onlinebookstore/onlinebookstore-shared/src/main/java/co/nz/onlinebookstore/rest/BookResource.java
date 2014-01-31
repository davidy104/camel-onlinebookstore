package co.nz.onlinebookstore.rest;

import javax.ws.rs.core.Response;

import co.nz.onlinebookstore.data.BookDto;

public interface BookResource {

	Response createBook(BookDto book);

	Response getBookByName(String bookName);

	Response getAllBooks();

	Response updateBook(Long bookId, BookDto book);

}
