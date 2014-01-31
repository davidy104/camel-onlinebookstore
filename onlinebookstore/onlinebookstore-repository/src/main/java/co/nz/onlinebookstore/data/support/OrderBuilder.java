package co.nz.onlinebookstore.data.support;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import co.nz.onlinebookstore.data.CustomerModel;
import co.nz.onlinebookstore.data.OrderModel;

@Component
public class OrderBuilder extends EntityBuilder<OrderModel> {

	@Override
	void initProduct() {
	}

	public OrderBuilder create(CustomerModel customer, Integer qty,
			BigDecimal totalPrice) {
		product = OrderModel.getBuilder(customer, qty, totalPrice).build();
		return this;
	}

	// public OrderBuilder addBooks(BookModel... books) {
	// OrderBookModel orderBook = null;
	// for (BookModel book : books) {
	// orderBook = OrderBookModel.getBuilder(product, book).build();
	// book.addOrderBookModel(orderBook);
	// product.addOrderBookModel(orderBook);
	// }
	// return this;
	// }

	@Override
	OrderModel assembleProduct() {
		return product;
	}

}
