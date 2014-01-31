package co.nz.onlinebookstore.test

import groovy.util.logging.Slf4j

import javax.annotation.Resource

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import co.nz.onlinebookstore.config.ApplicationConfiguration
import co.nz.onlinebookstore.data.BookDto
import co.nz.onlinebookstore.service.BookService

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@Slf4j
@Ignore
class BookServiceTest {

	@Resource
	BookService bookService

	@Test
	void testGetBookByName(){
		BookDto book = bookService.getBookByName('Camel in action')
		log.info "get book:{} $book"
	}
}
