package co.nz.onlinebookstore.ws;

import java.util.Set;

import javax.jws.WebParam;
import javax.jws.WebService;

import co.nz.onlinebookstore.data.BookDto;
import co.nz.onlinebookstore.data.CustomerDto;

@WebService
public interface OnlineBookStoreWS {
	BookDto getBookByIsbn(@WebParam String isbn) throws FaultMessage;

	BookDto getBookByName(@WebParam String bookName) throws FaultMessage;

	Set<BookDto> getAllBooks();

	CustomerDto getCustomerByID(@WebParam String personID) throws FaultMessage;

	CustomerDto createCustomer(@WebParam CustomerDto customer)
			throws FaultMessage;
}
