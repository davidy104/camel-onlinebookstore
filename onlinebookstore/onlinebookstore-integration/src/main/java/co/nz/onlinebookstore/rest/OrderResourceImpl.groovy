package co.nz.onlinebookstore.rest

import groovy.util.logging.Slf4j

import javax.annotation.Resource
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Response

import org.springframework.stereotype.Component

import co.nz.onlinebookstore.GenericAPIError
import co.nz.onlinebookstore.data.OrderDto
import co.nz.onlinebookstore.service.OrderService
import co.nz.onlinebookstore.service.OrderService.LoadStrategies
import co.nz.onlinebookstore.utils.OnlinebookstoreAPIUtils

@Path("/order")
@Slf4j
@Component
class OrderResourceImpl implements OrderResource{

	@Resource
	OrderService orderService

	@Override
	@GET
	@Produces("application/json")
	@Path("/queryByNo")
	Response getOrderByOrderNo(@QueryParam("orderNo") String orderNo) {
		log.info "getOrderByOrderNo start:{} $orderNo"
		OrderDto found
		GenericAPIError genericAPIError
		try {
			found = orderService.getOrderByOrderNo(orderNo, LoadStrategies.BOOK,LoadStrategies.CUSTOMER)
		} catch (e) {
			genericAPIError = OnlinebookstoreAPIUtils.errorHandle(e)
		}
		log.info "getOrderByOrderNo end:{}"
		return OnlinebookstoreAPIUtils.buildResponse(found, genericAPIError)
	}
}
