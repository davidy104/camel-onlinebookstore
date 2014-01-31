package co.nz.onlinebookstore.service;

import static co.nz.onlinebookstore.data.predicates.BookPredicates.findByBookName;
import static co.nz.onlinebookstore.data.predicates.BookPredicates.findByIsbn;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nz.onlinebookstore.DuplicatedException;
import co.nz.onlinebookstore.NotFoundException;
import co.nz.onlinebookstore.converter.BookConverter;
import co.nz.onlinebookstore.data.BookDto;
import co.nz.onlinebookstore.data.BookModel;
import co.nz.onlinebookstore.data.repository.BookRepository;
import co.nz.onlinebookstore.utils.OnlineBookStoreUtils;

@Service("bookService")
@Transactional(value = "localTxManager", readOnly = true)
public class BookServiceImpl implements BookService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BookServiceImpl.class);

	@Resource
	private BookRepository bookRepository;

	@Resource
	private BookConverter bookConverter;

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public Long createBook(BookDto book) throws Exception {
		Long addedId = null;
		LOGGER.info("create Book start:{}", book);
		String bookName = book.getBookName();
		if (bookRepository.findOne(findByBookName(bookName)) != null) {
			throw new DuplicatedException("Book already existed");
		}
		BookModel model = bookConverter.toModel(book);
		model = bookRepository.save(model);
		addedId = model.getBookId();

		LOGGER.info("create Book end:{}", addedId);
		return addedId;
	}

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public void updateBook(Long bookId, BookDto book) throws Exception {
		LOGGER.info("updateBook Book start:{}", book);
		BookModel bookModel = bookRepository.findOne(bookId);
		if (bookModel == null) {
			throw new NotFoundException("Book not found by id[" + bookId + "]");
		}
		if (!StringUtils.isEmpty(book.getBookName())) {
			if (bookRepository.findOne(findByBookName(book.getBookName())) != null) {
				throw new DuplicatedException("Book already existed");
			}
		}

		if (!StringUtils.isEmpty(book.getAuthor())) {
			bookModel.setAuthor(book.getAuthor());
		}
		if (!StringUtils.isEmpty(book.getPublishDate())) {
			bookModel.setPublishDate(OnlineBookStoreUtils.strToDate(book
					.getPublishDate()));
		}
		if (book.getPrice() != null) {
			bookModel.setPrice(book.getPrice());
		}

		if (book.getInventoryAmount() != null) {
			bookModel.setInventoryAmount(book.getInventoryAmount());
		}
		LOGGER.info("updateBook Book end:{}");
	}

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public void deleteBook(Long bookId) throws Exception {
		BookModel bookModel = bookRepository.findOne(bookId);
		if (bookModel == null) {
			throw new NotFoundException("Book not found by id[" + bookId + "]");
		}
		bookRepository.delete(bookModel);
	}

	@Override
	public BookDto getBookById(Long bookId) throws Exception {
		LOGGER.info("getBookById start:{}", bookId);
		BookDto found = null;
		BookModel bookModel = bookRepository.findOne(bookId);
		if (bookModel == null) {
			throw new NotFoundException("Book not found by id[" + bookId + "]");
		}
		found = bookConverter.toDto(bookModel);
		LOGGER.info("getBookById end:{}", found);
		return found;
	}

	@Override
	public BookDto getBookByIsbn(String isbn) throws Exception {
		LOGGER.info("getBookByIsbn start:{}", isbn);
		BookDto found = null;
		BookModel foundModel = bookRepository.findOne(findByIsbn(isbn));
		if (foundModel == null) {
			throw new NotFoundException("Book not found by isbn [" + isbn + "]");
		}
		found = bookConverter.toDto(foundModel);
		LOGGER.info("getBookByIsbn end:{}", found);
		return found;
	}

	@Override
	public BookDto getBookByName(String bookName) throws Exception {
		LOGGER.info("getBookByName start:{}", bookName);
		BookDto found = null;
		BookModel foundModel = bookRepository.findOne(findByBookName(bookName));
		if (foundModel == null) {
			throw new NotFoundException("Book not found by name [" + bookName
					+ "]");
		}
		found = bookConverter.toDto(foundModel);
		LOGGER.info("getBookByName end:{}", found);
		return found;
	}

	@Override
	public Set<BookDto> getAllBooks() throws Exception {
		LOGGER.info("getAllBooks start:{}");
		Set<BookDto> foundBooks = null;
		List<BookModel> bookModels = bookRepository.findAll();
		if (bookModels != null && bookModels.size() > 0) {
			foundBooks = new HashSet<BookDto>();
			for (BookModel bookModel : bookModels) {
				foundBooks.add(bookConverter.toDto(bookModel));
			}
		}
		LOGGER.info("getAllBooks end:{}");
		return foundBooks;
	}

}
