package co.nz.onlinebookstore.service;

import java.util.Set;

import co.nz.onlinebookstore.data.CustomerDto;

public interface CustomerService {
	CustomerDto createCustomer(CustomerDto customer) throws Exception;
	void updateCustomer(Long customerId, CustomerDto customer) throws Exception;
	void deleteCustomer(Long customerId) throws Exception;
	CustomerDto getCustomerById(Long customerId, boolean loadOrders)
			throws Exception;
	CustomerDto getCustomerByID(String customerID, boolean loadOrders)
			throws Exception;
	Set<CustomerDto> getCustomersByName(String customerName, boolean loadOrders)
			throws Exception;
}
