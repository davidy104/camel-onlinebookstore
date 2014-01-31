package co.nz.onlinebookstore.route;

import javax.annotation.Resource;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.idempotent.jdbc.JdbcMessageIdRepository;
import org.springframework.stereotype.Component;

import co.nz.onlinebookstore.mapping.OrderFormatTransformer;
import co.nz.onlinebookstore.service.OrderService;
import co.nz.onlinebookstore.support.OrderExceptionHandle;
/**
 *
 * @author david
 *
 *         the default EIP is InOut And in this case, a message is sent to a JMS
 *         queue, and as you do InOut, then a reply message is expected to be
 *         send back to the queue defined in the JMSReply property (which Camel
 *         will set to a temporary queue name if not explicit set otherwise).
 *         And if there is no reply message coming back after 20 seconds Camel
 *         fails with a timed out exception. You can configure the length of the
 *         timeout. So you have to decide if you are doing a request reply EIP
 *         (InOut) or a event message (InOnly)
 *
 */
@Component("onlineBookStoreOrderRoute")
public class OnlineBookStoreOrderRoute extends RouteBuilder {

	public static final String ENDPOINT = "jms:queue:bookStoreOrderInbound?transacted=true";
	public static final String OUTBOUND_END_POINT = "jms:queue:bookStoreOrderOutbound?jmsMessageType=Text";
	public static final String ROUTEID = "orderRoute";

	public static final String ORDER_DUPLICATED_ENDPOINT = "direct:duplicatedProcess";
	public static final String ORDER_REQULAR_ENDPOINT = "direct:regularProcess";

	@Resource
	private OrderService orderService;

	@Resource
	private OrderFormatTransformer orderFormatTransformer;

	@Resource
	private OrderExceptionHandle orderExceptionHandle;

	@Resource
	private JdbcMessageIdRepository orderJdbcMessageIdRepository;

	@SuppressWarnings("unchecked")
	@Override
	public void configure() throws Exception {

		from(ENDPOINT)
				.routeId(ROUTEID)
				.setExchangePattern(ExchangePattern.InOnly)
				.onException(Exception.class, ExchangeTimedOutException.class)
				.handled(true)
				.bean(orderExceptionHandle, "handle")
				.end()
				.log("Received message ${header[messageId]}")
				.transacted("PROPAGATION_REQUIRED")
				.idempotentConsumer(header("messageId"),
						orderJdbcMessageIdRepository).skipDuplicate(false)
				.choice().when(property(Exchange.DUPLICATE_MESSAGE))
				.to("direct:duplicatedProcess").otherwise()
				.to("direct:regularProcess").endChoice()
				// .to("controlbus:route?routeId=" + ROUTEID
				// + "&action=stop&async=true")
				.log("Signalled to stop route");

		from(ORDER_DUPLICATED_ENDPOINT)
				.routeId(ORDER_DUPLICATED_ENDPOINT)
				.to("log:co.nz.onlinebookstore.route.OnlineBookStoreOrderRoute?showAll=true&level=INFO")
				// mock endpoint only for testing
				.to("mock:duplicate")
				.to("sql:select order_id from ORDER_REQ_MESSAGE where message_id = :#messageId")
				.setHeader("orderId", simple("${body.get(0).get('ORDER_ID')}"))
				.log("orderId from Header ${header[orderId]}").choice()
				.when(header("orderId").isNotNull())
				.to("sql:select * from T_ORDER where ORDER_ID = :#orderId")
				.setBody(simple("${body.get(0)}"))
				.log("get processed order meta data ${body}")
				.bean(orderFormatTransformer, "respXmlMarshalFromQueryMap")
				.log("after marshal ${body}")
				.setHeader("duplicatedOrder", constant("yes"))
				.to(OUTBOUND_END_POINT).endChoice();

		from(ORDER_REQULAR_ENDPOINT)
				.routeId(ORDER_REQULAR_ENDPOINT)
				.bean(orderFormatTransformer, "reqXmlUnmarshal")
				.bean(orderService, "createOrder")
				.setHeader("orderId", simple("${body.orderId}"))
				.bean(orderFormatTransformer, "respXmlMarshal")
				.to("sql:update ORDER_REQ_MESSAGE set order_id = :#orderId where message_id = :#messageId")
				.to("log:output")
				.to(OUTBOUND_END_POINT)
				.to("controlbus:route?routeId=" + ORDER_REQULAR_ENDPOINT
						+ "&action=stop&async=true");
	}
}
