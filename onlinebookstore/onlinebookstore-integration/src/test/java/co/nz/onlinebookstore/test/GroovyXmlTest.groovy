package co.nz.onlinebookstore.test

import groovy.util.logging.Slf4j

import org.junit.Ignore
import org.junit.Test

import co.nz.onlinebookstore.data.BookDto
import co.nz.onlinebookstore.data.OrderDto
import co.nz.onlinebookstore.mapping.OrderFormatTransformer

@Slf4j
@Ignore
class GroovyXmlTest {

	OrderFormatTransformer orderFormatTransformer = new OrderFormatTransformer()

	@Test
	void testOrderReqXmlUnmarshal(){
		String xmlStr = """
			<order>
	 			<customer name="david">
	  				<person-id>849839483</person-id>
	  				<email>david.yuan@yellow.co.nz</email>
	 			</customer>
	 			<order-list>
	 				<book name="Camel in action" amount="12"/>
	 				<book name="Spring in action" amount="23"/>
	  			</order-list>
			</order>
		"""
		OrderDto order = orderFormatTransformer.reqXmlUnmarshal(xmlStr)
		log.info "order:{} $order"
	}

	@Test
	void testOrderRespXmlMarshal(){
		OrderDto orderDto = new OrderDto(orderNo:'yuiu18928918',orderTime:'2013-12-12 12:23:34',deliverTime:'2013-12-14 12:45:31',
		totalPrice:new BigDecimal(50.00),delivery:'Yes')
		BookDto camelBook = new BookDto(bookName:'Camel in action',isbn:'6712716271',publishDate:'2013-07-07',price:new BigDecimal(20.00),author:'Mike',inventoryAmount:50)
		BookDto springBook = new BookDto(bookName:'Spring in action',isbn:'989898989',publishDate:'2013-11-21',price:new BigDecimal(30.00),author:'Jon',inventoryAmount:20)
		orderDto.addBook(camelBook)
		orderDto.addBook(springBook)
		orderDto.customerName='david'
		orderDto.customerID='72827838293892'
		orderDto.email='david.yuan@yellow.co.nz'

		String xmlStr = orderFormatTransformer.respXmlMarshal(orderDto)
		log.info "xmlStr:{} $xmlStr"
	}
}
