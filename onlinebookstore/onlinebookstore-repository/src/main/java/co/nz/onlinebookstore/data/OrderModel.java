package co.nz.onlinebookstore.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
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
@Table(name = "T_ORDER")
public class OrderModel implements Serializable {

	private static final long serialVersionUID = 7885145761622254048L;

	public enum DeliveryStatus {
		delivered(0), notDelivered(1);
		DeliveryStatus(int value) {
			this.value = value;
		}

		private final int value;

		public int value() {
			return value;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDER_ID", insertable = false, updatable = false)
	private Long orderId;

	@Column(name = "ORDER_NO")
	private String orderNo;

	@Column(name = "TOTAL_PRICE")
	private BigDecimal totalPrice = BigDecimal.ZERO;

	@Column(name = "QTY")
	private Integer qty = 0;

	@Column(name = "ORDER_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderTime;

	@Column(name = "DELIVERY_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deliveryTime;

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "orderBookPK.order")
	private List<OrderBookModel> orderBookModels;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID")
	private CustomerModel customer;

	@Column(name = "DELIVERY_STATUS")
	private Integer deliveryStatus = DeliveryStatus.notDelivered.value();

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public List<OrderBookModel> getOrderBookModels() {
		return orderBookModels;
	}

	public void setOrderBookModels(List<OrderBookModel> orderBookModels) {
		this.orderBookModels = orderBookModels;
	}

	public void deliverOrder() {
		this.deliveryStatus = DeliveryStatus.delivered.value();
		this.deliveryTime = new Date();
	}

	public void addOrderBookModel(OrderBookModel orderBookModel) {
		if (orderBookModels == null) {
			orderBookModels = new ArrayList<OrderBookModel>();
		}
		orderBookModels.add(orderBookModel);
	}

	public CustomerModel getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerModel customer) {
		this.customer = customer;
	}

	public static Builder getBuilder(CustomerModel customer) {
		return new Builder(customer);
	}

	public static Builder getBuilder(CustomerModel customer, Integer qty,
			BigDecimal totalPrice) {
		return new Builder(customer, qty, totalPrice);
	}

	public static class Builder {

		private OrderModel built;

		public Builder(CustomerModel customer) {
			built = new OrderModel();
			built.orderNo = "ordNo:" + UUID.randomUUID().toString();
			built.customer = customer;
			built.orderTime = new Date();
		}

		public Builder(CustomerModel customer, Integer qty,
				BigDecimal totalPrice) {
			built = new OrderModel();
			built.orderNo = "ordNo:" + UUID.randomUUID().toString();
			built.customer = customer;
			built.orderTime = new Date();
			built.qty = qty;
			built.totalPrice = totalPrice;
		}

		public OrderModel build() {
			return built;
		}
	}

	@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(this.orderNo, ((OrderModel) obj).orderNo)
				.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(this.orderNo).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("orderId", orderId).append("orderNo", orderNo)
				.append("totalPrice", totalPrice).append("qty", qty)
				.append("deliveryStatus", deliveryStatus)
				.append("deliveryTime", deliveryTime)
				.append("orderTime", orderTime).toString();
	}
}
