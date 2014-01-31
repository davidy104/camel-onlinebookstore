package co.nz.onlinebookstore.service;

import co.nz.onlinebookstore.data.OrderDto;

public interface OrderReqMessageService {

	void updateOrderReqMessageWithOrder(String messageId, OrderDto order);

}
