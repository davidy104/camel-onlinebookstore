package co.nz.onlinebookstore.converter

import groovy.util.logging.Slf4j

import javax.annotation.Resource

import org.springframework.stereotype.Component

import co.nz.onlinebookstore.data.CustomerDto
import co.nz.onlinebookstore.data.CustomerModel
import co.nz.onlinebookstore.data.OrderModel
import co.nz.onlinebookstore.service.Converter

@Slf4j
@Component("customerConverter")
class CustomerConverter implements Converter<CustomerDto,CustomerModel>{

	@Resource
	OrderConverter orderConverter

	@Override
	CustomerModel toModel(CustomerDto dto) {
		log.debug "toModel start:{} $dto"
		CustomerModel model = new CustomerModel(name:dto.customerName,email:dto.email,personID:dto.customerID)
		log.debug "toModel end:{} $model"
		return model
	}

	@Override
	CustomerDto toDto(CustomerModel model, Object... loadStrategies) {
		log.debug "toDto start:{} $model"

		boolean loadOrders = false
		if(loadStrategies){
			loadOrders = (Boolean)loadStrategies[0]
		}
		log.debug "loadOrders:{} $loadOrders"
		CustomerDto dto = new CustomerDto(customerId:model.customerId,customerName:model.name,customerID:model.personID,email:model.email)

		if(loadOrders){
			if(model.orders){
				model.orders.each {OrderModel order->
					dto.addOrder(orderConverter.toDto(order))
				}
			}
		}
		return dto
	}
}
