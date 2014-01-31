package co.nz.onlinebookstore.rest

import groovy.util.logging.Slf4j

import javax.annotation.Resource
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Response

import org.springframework.stereotype.Component

import co.nz.onlinebookstore.GenericAPIError
import co.nz.onlinebookstore.data.BookDto
import co.nz.onlinebookstore.service.BookService
import co.nz.onlinebookstore.utils.OnlinebookstoreAPIUtils

@Path("/book")
@Slf4j
@Component
class BookResourceImpl implements BookResource{

	@Resource
	BookService bookService

	@Override
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/add")
	Response createBook(BookDto book) {
		log.info "createBook start:{} $book"
		Long bookId
		GenericAPIError genericAPIError
		try {
			bookId = bookService.createBook(book)
		} catch (e) {
			genericAPIError = OnlinebookstoreAPIUtils.errorHandle(e)
		}
		log.info "createBook end:{}"
		return OnlinebookstoreAPIUtils.buildResponse(bookId, genericAPIError)
	}

	@Override
	@Path("/search")
	@GET
	@Produces("application/json")
	public Response getBookByName(@QueryParam("bookName")String bookName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Path("/all")
	@GET
	@Produces("application/json")
	public Response getAllBooks() {
		log.info "getAllBooks start:{}"
		Set<BookDto> foundBooks
		GenericAPIError genericAPIError
		try {
			foundBooks = bookService.getAllBooks()
		} catch (e) {
			genericAPIError = OnlinebookstoreAPIUtils.errorHandle(e)
		}
		log.info "getAllBooks end:{}"
		return OnlinebookstoreAPIUtils.buildResponse(foundBooks, genericAPIError)
	}

	@Override
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/{bookId}")
	public Response updateBook(@PathParam("bookId")Long bookId, BookDto book) {
		// TODO Auto-generated method stub
		return null;
	}
}
