package co.nz.onlinebookstore.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "T_CUSTOMER")
public class CustomerModel implements Serializable {

	private static final long serialVersionUID = -2044121939035731690L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CUSTOMER_ID", insertable = false, updatable = false)
	private Long customerId;

	@Column(name = "CUSTOMER_NAME")
	private String name;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PERSON_ID")
	private String personID;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private List<OrderModel> orders;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<OrderModel> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderModel> orders) {
		this.orders = orders;
	}

	public void addOrder(OrderModel order) {
		if (orders == null) {
			orders = new ArrayList<OrderModel>();
		}
		orders.add(order);
	}

	public static Builder getBuilder(String name, String email, String personID) {
		return new Builder(name, email, personID);
	}

	public static class Builder {

		private CustomerModel built;

		public Builder(String name, String email, String personID) {
			built = new CustomerModel();
			built.name = name;
			built.email = email;
			built.personID = personID;
		}

		public CustomerModel build() {
			return built;
		}
	}

	@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(this.personID, ((CustomerModel) obj).personID)
				.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(this.personID).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("customerId", customerId).append("name", name)
				.append("personID", personID).append("email", email).toString();
	}
}
