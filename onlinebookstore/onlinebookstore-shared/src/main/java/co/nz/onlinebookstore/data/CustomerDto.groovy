package co.nz.onlinebookstore.data

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["customerId"])

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class CustomerDto {

	@XmlElement
	Long customerId

	@XmlElement
	String customerName

	@XmlElement
	String customerID

	@XmlElement
	String email

	Set<OrderDto> orders

	void addOrder(OrderDto order){
		if(!orders){
			orders = new HashSet<OrderDto>()
		}
		orders.add(order)
	}
}
