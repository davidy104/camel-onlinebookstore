package co.nz.onlinebookstore.data.support;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Component;

import co.nz.onlinebookstore.data.BookModel;

@Component
public class BookBuilder extends EntityBuilder<BookModel> {

	@Override
	void initProduct() {
	}

	public BookBuilder create(String isbn, String bookName, BigDecimal price,
			String author, Date publishDate, Integer inventoryAmount) {
		product = BookModel.getBuilder(isbn, bookName, price, author,
				publishDate, inventoryAmount).build();
		return this;
	}

	@Override
	BookModel assembleProduct() {
		return this.product;
	}

}
