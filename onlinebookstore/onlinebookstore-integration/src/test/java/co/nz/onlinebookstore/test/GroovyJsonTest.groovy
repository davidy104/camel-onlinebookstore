package co.nz.onlinebookstore.test

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j

import org.junit.Ignore;
import org.junit.Test

import co.nz.onlinebookstore.data.BookDto
import co.nz.onlinebookstore.data.CustomerDto
import co.nz.onlinebookstore.data.OrderDto
import co.nz.onlinebookstore.mapping.OrderFormatTransformer;

@Slf4j
@Ignore
class GroovyJsonTest {

	OrderFormatTransformer orderFormatTransformer = new OrderFormatTransformer()

	@Test
	void testRespOrderJsonMarshal(){
		OrderDto orderDto = new OrderDto(orderNo:'yuiu18928918',orderTime:'2013-12-12 12:23:34',deliverTime:'2013-12-14 12:45:31',
		totalPrice:new BigDecimal(50.00),delivery:'Yes')
		BookDto camelBook = new BookDto(bookName:'Camel in action',isbn:'6712716271',publishDate:'2013-07-07',price:new BigDecimal(20.00),author:'Mike',inventoryAmount:50)
		BookDto springBook = new BookDto(bookName:'Spring in action',isbn:'989898989',publishDate:'2013-11-21',price:new BigDecimal(30.00),author:'Jon',inventoryAmount:20)
		orderDto.addBook(camelBook)
		orderDto.addBook(springBook)
		orderDto.customerName='david'
		orderDto.customerID='72827838293892'
		orderDto.email='david.yuan@yellow.co.nz'

		String jsonStr = orderFormatTransformer.respJsonMarshal(orderDto)
		log.info "jsonStr:{} $jsonStr"
	}

	@Test
	void testOrderDtoMarshalUnmarshal() {

		JsonBuilder jsonBuilder = new JsonBuilder()
		jsonBuilder{
			customer{
				customerName 'david'
				customerID '3827382738'
				email 'david.yuan@yellow.co.nz'
			}
			books ({bookName 'Camel in action'},{bookName 'Spring in action'})
		}
		String jsonStr = jsonBuilder.toString()
		log.info "jsonStr:{} $jsonStr"

		OrderDto orderDto = new OrderDto()
		def jsonSlurper = new JsonSlurper();
		Object result= jsonSlurper.parseText(jsonStr)

		Map jsonResult = (Map) result
		Map custMap = (Map) jsonResult.get("customer");
		List books = (List) jsonResult.get("books")
		CustomerDto customerDto = new CustomerDto(customerName:"${custMap.get('customerName')}",customerID:"${custMap.get('customerID')}",
		email:"${custMap.get('email')}")

		log.info "customer:{} $customerDto"
		orderDto.customer = customerDto

		books.each {bookObj->
			Map bookMap = (Map)bookObj
			BookDto book = new BookDto(bookName:"${bookMap.get('bookName')}")
			orderDto.addBook(book)
		}

		log.info "order:{} $orderDto"
	}
}
