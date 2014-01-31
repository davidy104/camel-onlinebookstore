package co.nz.onlinebookstore.data.predicates;

import co.nz.onlinebookstore.data.QOrderReqMessage;

import com.mysema.query.types.Predicate;

public class OrderReqMessagePredicate {
	public static Predicate findProcessedReqMessage(final String messageId) {
		QOrderReqMessage orderReqMessage = QOrderReqMessage.orderReqMessage;
		return (orderReqMessage.messageId.eq(messageId))
				.and(orderReqMessage.order.isNotNull());
	}
}
