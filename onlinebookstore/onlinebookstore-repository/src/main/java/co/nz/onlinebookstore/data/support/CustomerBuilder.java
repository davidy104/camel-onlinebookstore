package co.nz.onlinebookstore.data.support;

import org.springframework.stereotype.Component;

import co.nz.onlinebookstore.data.CustomerModel;

@Component
public class CustomerBuilder extends EntityBuilder<CustomerModel> {
	@Override
	void initProduct() {
	}

	public CustomerBuilder create(String name, String email, String personID) {
		product = CustomerModel.getBuilder(name, email, personID).build();
		return this;
	}

	@Override
	CustomerModel assembleProduct() {
		return this.product;
	}
}
