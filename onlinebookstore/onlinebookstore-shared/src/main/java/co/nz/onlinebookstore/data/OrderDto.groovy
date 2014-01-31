package co.nz.onlinebookstore.data

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

import co.nz.onlinebookstore.utils.BigDecimalAdapter

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["orderId","orderNo"])

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class OrderDto {
	@XmlElement
	Long orderId

	@XmlElement
	String orderNo

	@XmlElement
	String orderTime

	@XmlElement
	String deliverTime

	@XmlElement
	@XmlJavaTypeAdapter(BigDecimalAdapter.class)
	BigDecimal totalPrice

	Set<BookDto> books

	@XmlElement
	String delivery

	@Delegate
	CustomerDto customer = new CustomerDto()

	void addBook(BookDto book){
		if(!books){
			books = new HashSet<BookDto>()
		}
		books << book
	}
}
