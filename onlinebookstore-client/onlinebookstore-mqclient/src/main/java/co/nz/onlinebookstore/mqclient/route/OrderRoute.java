package co.nz.onlinebookstore.mqclient.route;

import javax.annotation.Resource;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component("orderRoute")
public class OrderRoute extends RouteBuilder {

	/**
	 * In this example the fixed reply queue named "bookStoreOrderOutbound" is
	 * used. By default Camel assumes the queue is shared when using fixed reply
	 * queues, and therefore it uses a JMSSelector to only pickup the expected
	 * reply messages
	 *
	 * However there is a drawback doing this as JMS selectos is slower.if the
	 * fixed reply queue is exclusive to the Camel reply consumer, then we can
	 * avoid using the JMS selectors, and thus be more performant. eg.
	 * "jms:queue:bookStoreOrderInbound?transacted=true&replyTo=bookStoreOrderOutbound&replyToType=Exclusive"
	 */
	private static final String MQ_ENDPOINT = "jms:queue:bookStoreOrderInbound?transacted=true&replyTo=bookStoreOrderOutbound&replyToType=Exclusive&requestTimeout=10s";
	private static final String ENDPOINT = "direct:order";

	// for testing
	@Resource
	private OrderRespProcessor orderRespProcessor;

	@Override
	public void configure() throws Exception {
		from(ENDPOINT).routeId(ENDPOINT)
				.setExchangePattern(ExchangePattern.InOut)
				.to("log:myLog?showAll=true").to(MQ_ENDPOINT)
				.log("jmsMsgId set = ${in.header.JMSMessageId}")
				.to("log:myLog?showAll=true")
				.process(orderRespProcessor)
				.log("get processed billing meta data ${body}")
				.end();
	}
}
