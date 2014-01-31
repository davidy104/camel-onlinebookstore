package co.nz.onlinebookstore.service;

import static co.nz.onlinebookstore.data.predicates.BookPredicates.findByBookName;
import static co.nz.onlinebookstore.data.predicates.CustomerPredicates.findByID;
import static co.nz.onlinebookstore.data.predicates.OrderPredicates.findByOrderNo;

import java.math.BigDecimal;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.nz.onlinebookstore.NotFoundException;
import co.nz.onlinebookstore.converter.BookConverter;
import co.nz.onlinebookstore.converter.CustomerConverter;
import co.nz.onlinebookstore.converter.OrderConverter;
import co.nz.onlinebookstore.data.BookDto;
import co.nz.onlinebookstore.data.BookModel;
import co.nz.onlinebookstore.data.CustomerDto;
import co.nz.onlinebookstore.data.CustomerModel;
import co.nz.onlinebookstore.data.OrderBookModel;
import co.nz.onlinebookstore.data.OrderDto;
import co.nz.onlinebookstore.data.OrderModel;
import co.nz.onlinebookstore.data.repository.BookRepository;
import co.nz.onlinebookstore.data.repository.CustomerRepository;
import co.nz.onlinebookstore.data.repository.OrderRepository;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderServiceImpl.class);

	@Resource
	private OrderRepository orderRepository;

	@Resource
	private CustomerRepository customerRepository;

	@Resource
	private BookRepository bookRepository;

	@Resource
	private OrderConverter orderConverter;

	@Resource
	private BookConverter bookConverter;

	@Resource
	private CustomerConverter customerConverter;

	@Override
	public OrderDto createOrder(OrderDto order) throws Exception {
		LOGGER.info("createOrder start:{}", order);
		BigDecimal totalPrice = BigDecimal.ZERO;
		Integer qty = 0;
		OrderDto added = null;
		BookModel bookModel = null;
		CustomerDto customerDto = order.getCustomer();
		Set<BookDto> orderBooks = order.getBooks();
		OrderModel addedModel = null;
		CustomerModel customerModel = null;
		if (customerDto != null
				&& !StringUtils.isEmpty(customerDto.getCustomerID())
				&& orderBooks != null && orderBooks.size() > 0) {
			addedModel = orderConverter.toModel(order);
			String customerID = customerDto.getCustomerID();
			customerModel = customerRepository.findOne(findByID(customerID));
			if (customerModel == null) {
				customerModel = customerConverter.toModel(customerDto);
				customerModel = customerRepository.save(customerModel);
			}
			LOGGER.info("customer:{}", customerModel);
			addedModel.setCustomer(customerModel);

			for (BookDto bookDto : orderBooks) {
				String bookName = bookDto.getBookName();
				BigDecimal totalPricePerbook = BigDecimal.ZERO;
				Integer orderAmt = bookDto.getOrderAmount();
				bookModel = bookRepository.findOne(findByBookName(bookName));
				if (bookModel == null) {
					throw new NotFoundException("Order Book Not found by name["
							+ bookName + "]");
				}
				totalPricePerbook = bookModel.getPrice().multiply(
						new BigDecimal(orderAmt));
				totalPrice = totalPrice.add(totalPricePerbook);
				qty += orderAmt;
				OrderBookModel orderBookModel = new OrderBookModel();
				orderBookModel.setBookModel(bookModel);
				orderBookModel.setOrderModel(addedModel);
				orderBookModel.setAmount(orderAmt);
				orderBookModel.setTotalPrice(totalPricePerbook);
				LOGGER.info("OrderBookModel:{} ", orderBookModel);
				addedModel.addOrderBookModel(orderBookModel);
			}

			addedModel.setTotalPrice(totalPrice);
			addedModel.setQty(qty);
			addedModel = orderRepository.save(addedModel);
			added = orderConverter.toDto(addedModel, LoadStrategies.CUSTOMER,
					LoadStrategies.BOOK);
			LOGGER.info("createOrder end:{}", added);
		}
		return added;
	}

	@Override
	public void cancelOrder(String orderNo) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deliverOrder(String orderNo, String accountNo) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public OrderDto getOrderByOrderNo(String orderNo,
			LoadStrategies... loadStrategies) throws Exception {
		LOGGER.info("getOrderByOrderNo start:{}", orderNo);
		OrderDto found = null;
		OrderModel orderModel = orderRepository.findOne(findByOrderNo(orderNo));
		if (orderModel == null) {
			throw new NotFoundException("Order not found by no[" + orderNo
					+ "]");
		}
		found = orderConverter.toDto(orderModel, loadStrategies);
		LOGGER.info("getOrderByOrderNo end:{}", found);
		return found;
	}

	@Override
	public OrderDto getOrderById(Long orderId, LoadStrategies... loadStrategies)
			throws Exception {
		LOGGER.info("getOrderById start:{}", orderId);
		OrderDto found = null;
		OrderModel orderModel = orderRepository.findOne(orderId);
		if (orderModel == null) {
			throw new NotFoundException("Order not found by id[" + orderId
					+ "]");
		}
		found = orderConverter.toDto(orderModel, loadStrategies);
		LOGGER.info("getOrderById end:{}", found);
		return found;
	}

}
