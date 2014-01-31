package co.nz.onlinebookstore.mapping

import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import groovy.xml.MarkupBuilder

import org.springframework.stereotype.Component

import co.nz.onlinebookstore.data.BookDto
import co.nz.onlinebookstore.data.CustomerDto
import co.nz.onlinebookstore.data.OrderDto
import co.nz.onlinebookstore.utils.OnlineBookStoreUtils;

@Component("orderFormatTransformer")
@Slf4j
class OrderFormatTransformer {

	/**
	 * <order>
	 * 	<customer name="">
	 * 		<person-id></person-id>
	 * 		<email></email>
	 * 	</customer>
	 * 	<order-list>
	 * 		<book name="" amount=""/>
	 * 		<book name="" amount=""/>
	 * 	</order-list>
	 * </order>
	 *
	 */
	OrderDto reqXmlUnmarshal(String xmlStr){
		log.info "reqXmlUnmarshal start:{} $xmlStr"
		def orderEle = new XmlSlurper().parseText(xmlStr)
		log.info "customer size:{} ${orderEle.customer.size()}"
		def customerEle = orderEle.customer
		OrderDto order = new OrderDto()
		CustomerDto customer = new CustomerDto(customerName:customerEle.@name,customerID:customerEle."person-id".text(),email:customerEle.email)
		log.info "customer:{} $customer"
		order.customer = customer
		def orderListEle = orderEle."order-list"
		if(orderListEle && orderListEle.book.size()>0){
			orderListEle.book.each {
				String bkName = it.@name
				Integer orderAmt = Integer.valueOf(it.@amount.text())
				BookDto book = new BookDto(bookName:bkName,orderAmount:orderAmt)
				order.addBook(book)
			}
		}

		log.info "reqXmlUnmarshal end:{} $order"
		return order
	}

	/**
	 * <order order-no="">
	 * 	<order-time></order-time>
	 * 	<deliver-time></deliver-time>
	 *  <total-price></total-price>
	 *  <delivery></delivery>
	 * 	<customer name="">
	 * 		<person-id></person-id>
	 * 		<email></email>
	 * 	</customer>
	 * 	<order-list>
	 * 		<book isbn="">
	 * 			<name></name>
	 * 			<price></price>
	 * 			<author></author>
	 * 			<publish-date></publish-date>
	 * 			<order-amount></order-amount>
	 * 		</book>
	 * 	</order-list>
	 * </order>
	 *
	 */
	String respXmlMarshal(OrderDto order){
		log.info "respXmlMarshal start:{} $order"
		String xmlStr
		Set<BookDto> books = order.books
		StringWriter sw = new StringWriter()
		MarkupBuilder builder = new MarkupBuilder(sw)
		builder.'order'('order-no': "${order.orderNo}") {
			'order-time' "${order.orderTime}"
			'deliver-time' "${order.deliverTime}"
			'total-price' "${order.totalPrice}"
			delivery "${order.delivery}"
			customer(name:"${order.customerName}") {
				'person-id' "${order.customerID}"
				email "${order.email}"
			}

			if(books && books.size() >0){
				log.info "book size:{} ${books.size()}"
			}

			if(books && books.size() >0){
				orderList(){
					books.each {BookDto bookDto->
						println "book:{} $bookDto"
						book(isbn:"${bookDto.isbn}"){
							name "${bookDto.bookName}"
							price "${bookDto.price}"
							author "${bookDto.author}"
							'publish-date' "${bookDto.publishDate}"
							'order-amount' "${bookDto.orderAmount}"
						}
					}
				}
			}
		}
		xmlStr = sw.toString()
		log.info "respXmlMarshal end:{} $xmlStr"
		return xmlStr
	}

	String respXmlMarshalFromQueryMap(Map orderMetaData){
		log.info "respXmlMarshalFromQueryMap start:{} $orderMetaData"
		String xmlStr
		StringWriter sw = new StringWriter()
		MarkupBuilder builder = new MarkupBuilder(sw)
		String orderTime = OnlineBookStoreUtils.dateToStr(orderMetaData['ORDER_TIME'])
		String deliverTime
		if(orderMetaData['DELIVERY_TIME']){
			deliverTime= OnlineBookStoreUtils.dateToStr(orderMetaData['DELIVERY_TIME'])
		}


		builder.'order'('order-no': "${orderMetaData['ORDER_NO']}") {
			'order-time' "${orderTime}"
			'deliver-time' "${deliverTime}"
			'total-price' "${orderMetaData['TOTAL_PRICE']}"
			delivery "${orderMetaData['DELIVERY_STATUS']}"
		}
		xmlStr = sw.toString()
		log.info "respXmlMarshalFromQueryMap end:{} $xmlStr"
		return xmlStr
	}

	//	String respJsonMarshal(OrderDto orderDto){
	//		log.info "respJsonMarshal start:{} $orderDto"
	//		JsonBuilder jsonBuilder = new JsonBuilder()
	//		Set<BookDto> bookSet = orderDto.books
	//
	//		jsonBuilder{
	//			orderId "${orderDto.orderId}"
	//			orderNo "${orderDto.orderNo}"
	//			orderTime "${orderDto.orderTime}"
	//			delivery "${orderDto.delivery}"
	//			deliverTime "${orderDto.deliverTime}"
	//
	//			customer{
	//				customerName "${orderDto.customerName}"
	//				customerID "${orderDto.customerID}"
	//				email "${orderDto.email}"
	//			}
	//
	//			if(bookSet){
	//				books (
	//					bookSet.collect{BookDto bookDto->
	//						book{
	//							bookId "${bookDto.bookId}"
	//							bookName "${bookDto.bookName}"
	//							isbn "${bookDto.isbn}"
	//							publishDate "${bookDto.publishDate}"
	//							price "${bookDto.price}"
	//							author "${bookDto.author}"
	//						}
	//					}.join(',')
	//				)
	//			}
	//		}
	//		String jsonStr = jsonBuilder.toString()
	//		log.info "respJsonMarshal end:{} $jsonStr"
	//		return jsonStr
	//	}
}
