package co.nz.onlinebookstore.service;

import co.nz.onlinebookstore.data.OrderDto;

public interface OrderService {

	enum LoadStrategies {
		CUSTOMER, BOOK
	}

	OrderDto createOrder(OrderDto order) throws Exception;
	void cancelOrder(String orderNo) throws Exception;
	void deliverOrder(String orderNo, String accountNo) throws Exception;
	OrderDto getOrderByOrderNo(String orderNo, LoadStrategies... loadStrategies)
			throws Exception;
	OrderDto getOrderById(Long orderId, LoadStrategies... loadStrategies)
			throws Exception;
}
