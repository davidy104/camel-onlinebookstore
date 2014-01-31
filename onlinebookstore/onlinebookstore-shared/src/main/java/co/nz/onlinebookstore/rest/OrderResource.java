package co.nz.onlinebookstore.rest;

import javax.ws.rs.core.Response;

public interface OrderResource {

	Response getOrderByOrderNo(String orderNo);

}
