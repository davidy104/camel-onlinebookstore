package co.nz.onlinebookstore.data.predicates;

import co.nz.onlinebookstore.data.QCustomerModel;

import com.mysema.query.types.Predicate;

public class CustomerPredicates {
	public static Predicate findByNameAndEmail(final String name,
			final String email) {
		QCustomerModel customer = QCustomerModel.customerModel;
		return (customer.name.eq(name)).and(customer.email.eq(email));
	}

	public static Predicate findByID(final String personID) {
		QCustomerModel customer = QCustomerModel.customerModel;
		return (customer.personID.eq(personID));
	}

	public static Predicate findByName(final String name) {
		QCustomerModel customer = QCustomerModel.customerModel;
		return (customer.name.eq(name));
	}
}
