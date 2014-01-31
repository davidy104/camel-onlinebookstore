package co.nz.onlinebookstore.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "T_BOOK")
public class BookModel implements Serializable {

	private static final long serialVersionUID = -4606445055076235211L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BOOK_ID", insertable = false, updatable = false)
	private Long bookId;

	@Column(name = "ISBN")
	private String isbn;

	@Column(name = "BOOK_NAME")
	private String bookName;

	@Column(name = "AUTHOR")
	private String author;

	@Column(name = "PUBLISH_DATE")
	@Temporal(TemporalType.DATE)
	private Date publishDate;

	@Column(name = "PRICE")
	private BigDecimal price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID")
	private OrderModel order;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderBookPK.book")
	private List<OrderBookModel> orderBookModels;

	@Column(name = "INVENTORY_AMT")
	private Integer inventoryAmount;

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public OrderModel getOrder() {
		return order;
	}

	public void setOrder(OrderModel order) {
		this.order = order;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public List<OrderBookModel> getOrderBookModels() {
		return orderBookModels;
	}

	public void setOrderBookModels(List<OrderBookModel> orderBookModels) {
		this.orderBookModels = orderBookModels;
	}

	public void addOrderBookModel(OrderBookModel orderBookModel) {
		if (orderBookModels == null) {
			orderBookModels = new ArrayList<OrderBookModel>();
		}
		orderBookModels.add(orderBookModel);
	}

	public Integer getInventoryAmount() {
		return inventoryAmount;
	}

	public void setInventoryAmount(Integer inventoryAmount) {
		this.inventoryAmount = inventoryAmount;
	}

	public static Builder getBuilder(String isbn, String bookName,
			BigDecimal price, String author, Date publishDate,
			Integer inventoryAmount) {
		return new Builder(isbn, bookName, price, author, publishDate,
				inventoryAmount);
	}

	public static Builder getBuilder(String isbn, String bookName,
			BigDecimal price, String author, Integer inventoryAmount) {
		return new Builder(isbn, bookName, price, author, inventoryAmount);
	}

	public static Builder getBuilder(String isbn, String bookName,
			Integer inventoryAmount) {
		return new Builder(isbn, bookName, inventoryAmount);
	}

	public static class Builder {

		private BookModel built;

		public Builder(String isbn, String bookName, BigDecimal price,
				String author, Date publishDate, Integer inventoryAmount) {
			built = new BookModel();
			built.isbn = isbn;
			built.price = price;
			built.bookName = bookName;
			built.author = author;
			built.publishDate = publishDate;
			built.inventoryAmount = inventoryAmount;
		}

		public Builder(String isbn, String bookName, BigDecimal price,
				String author, Integer inventoryAmount) {
			built = new BookModel();
			built.isbn = isbn;
			built.price = price;
			built.bookName = bookName;
			built.author = author;
			built.inventoryAmount = inventoryAmount;
		}

		public Builder(String isbn, String bookName, Integer inventoryAmount) {
			built = new BookModel();
			built.isbn = isbn;
			built.bookName = bookName;
			built.inventoryAmount = inventoryAmount;
		}

		public BookModel build() {
			return built;
		}
	}

	@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(this.isbn, ((BookModel) obj).isbn).isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(this.isbn).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("isbn", isbn).append("price", price)
				.append("bookName", bookName).append("author", author)
				.append("inventoryAmount", inventoryAmount).toString();
	}
}
