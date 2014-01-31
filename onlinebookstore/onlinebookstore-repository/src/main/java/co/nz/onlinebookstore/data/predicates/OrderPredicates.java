package co.nz.onlinebookstore.data.predicates;

import co.nz.onlinebookstore.data.QOrderModel;

import com.mysema.query.types.Predicate;

public class OrderPredicates {
	public static Predicate findByOrderNo(final String orderNo) {
		QOrderModel order = QOrderModel.orderModel;
		return order.orderNo.eq(orderNo);
	}
}
