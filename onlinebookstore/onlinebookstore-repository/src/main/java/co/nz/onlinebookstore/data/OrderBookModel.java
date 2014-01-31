package co.nz.onlinebookstore.data;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "T_ORDER_BOOK")
@AssociationOverrides({
		@AssociationOverride(name = "orderBookPK.order", joinColumns = @JoinColumn(name = "order_id")),
		@AssociationOverride(name = "orderBookPK.book", joinColumns = @JoinColumn(name = "book_id"))})
public class OrderBookModel implements Serializable {

	private static final long serialVersionUID = -7865110480567598850L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDER_BOOK_ID", insertable = false, updatable = false)
	private Long orderBookId;

	@Column(name = "AMOUNT")
	private Integer amount;

	@Column(name = "TOTAL_PRICE")
	private BigDecimal totalPrice;

	@Embedded
	private OrderBookPK orderBookPK = new OrderBookPK();

	public Long getOrderBookId() {
		return orderBookId;
	}

	public void setOrderBookId(Long orderBookId) {
		this.orderBookId = orderBookId;
	}

	public OrderBookPK getOrderBookPK() {
		return orderBookPK;
	}

	public void setOrderBookPK(OrderBookPK orderBookPK) {
		this.orderBookPK = orderBookPK;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Transient
	public OrderModel getOrderModel() {
		return getOrderBookPK().getOrder();
	}

	public void setOrderModel(OrderModel order) {
		getOrderBookPK().setOrder(order);
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Transient
	public BookModel getBookModel() {
		return getOrderBookPK().getBook();
	}

	public void setBookModel(BookModel book) {
		getOrderBookPK().setBook(book);
	}

	public static Builder getBuilder(OrderModel order, BookModel book,
			Integer amount) {
		return new Builder(order, book, amount);
	}

	public static class Builder {

		private OrderBookModel built;

		public Builder(OrderModel order, BookModel book, Integer amount) {
			built = new OrderBookModel();
			built.getOrderBookPK().setOrder(order);
			built.getOrderBookPK().setBook(book);
			built.amount = amount;
		}

		public OrderBookModel build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("orderBookId", orderBookId).append("amount", amount)
				.toString();

	}
}
