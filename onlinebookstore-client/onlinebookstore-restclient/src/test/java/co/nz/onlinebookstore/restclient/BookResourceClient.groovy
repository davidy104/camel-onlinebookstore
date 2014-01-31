package co.nz.onlinebookstore.restclient

import static org.junit.Assert.*
import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient

import org.junit.BeforeClass
import org.junit.Test

import co.nz.onlinebookstore.data.BookDto

@Slf4j
class BookResourceClient {
	static RESTClient client

	@BeforeClass
	static void initialize() throws Exception {
		client = new RESTClient('http://localhost:8085/onlinebookstore/rest/book/', ContentType.JSON)
	}

	@Test
	public void testGetBooks() {
		def response = client.get(path:'all')
		log.debug "status = $response.status"
		List<BookDto> books = response.data
		books.each() {obj-> println "$obj" }
	}
}
