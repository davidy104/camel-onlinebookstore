package co.nz.onlinebookstore.data;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Embeddable
public class OrderBookPK implements Serializable {

	private static final long serialVersionUID = -4704602339040618826L;

	@ManyToOne(fetch = FetchType.LAZY)
	private OrderModel order;

	@ManyToOne(fetch = FetchType.LAZY)
	private BookModel book;

	public OrderModel getOrder() {
		return order;
	}

	public void setOrder(OrderModel order) {
		this.order = order;
	}

	public BookModel getBook() {
		return book;
	}

	public void setBook(BookModel book) {
		this.book = book;
	}

	@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(this.order, ((OrderBookPK) obj).order)
				.append(this.book, ((OrderBookPK) obj).book).isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(this.order).append(this.book).toHashCode();
	}

}
