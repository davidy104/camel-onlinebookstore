package co.nz.onlinebookstore.converter

import groovy.util.logging.Slf4j

import javax.annotation.Resource

import org.springframework.stereotype.Component

import co.nz.onlinebookstore.data.BookDto
import co.nz.onlinebookstore.data.BookModel
import co.nz.onlinebookstore.data.OrderBookModel
import co.nz.onlinebookstore.data.OrderDto
import co.nz.onlinebookstore.data.OrderModel
import co.nz.onlinebookstore.service.Converter
import co.nz.onlinebookstore.service.OrderService.LoadStrategies
import co.nz.onlinebookstore.utils.OnlineBookStoreUtils

@Slf4j
@Component("orderConverter")
class OrderConverter implements Converter<OrderDto,OrderModel>{

	@Resource
	CustomerConverter customerConverter

	@Resource
	BookConverter bookConverter

	/**
	 * for order create
	 */
	@Override
	public OrderModel toModel(OrderDto dto) {
		log.debug "toModel start:{} $dto"
		OrderModel model = new OrderModel(orderNo:UUID.randomUUID().toString(),orderTime:new Date())
		log.debug "toModel end:{} $model"
		return model
	}

	@Override
	public OrderDto toDto(OrderModel model, Object... loadStrategies) {
		log.debug "toDto start:{} $model"
		OrderDto dto = new OrderDto(orderId:model.orderId,orderNo:model.orderNo,totalPrice:model.totalPrice,delivery:model.deliveryStatus)
		if(model.orderTime){
			dto.orderTime = OnlineBookStoreUtils.dateToStr(model.orderTime)
		}

		if(model.deliveryTime){
			dto.deliverTime = OnlineBookStoreUtils.dateToStr(model.deliveryTime)
		}

		if(loadStrategies){
			for(Object tmpLoadStrategy : loadStrategies){
				LoadStrategies loadStrategy = (LoadStrategies)tmpLoadStrategy
				log.debug "LoadStrategies:{} $loadStrategy"
				switch (loadStrategy) {
					case LoadStrategies.CUSTOMER:
						if(model.customer){
							dto.customer = customerConverter.toDto(model.customer, false)
						}
						break
					case LoadStrategies.BOOK:
						if(model.orderBookModels){
							model.orderBookModels.each {OrderBookModel orderBookModel->
								BookModel book = orderBookModel.bookModel
								BookDto bookDto = bookConverter.toDto(book)
								bookDto.orderAmount=orderBookModel.amount
								dto.addBook(bookDto)
							}
						}
				}
			}
		}

		log.debug "toDto end:{} $dto"
		return dto
	}
}
