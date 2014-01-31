package co.nz.onlinebookstore.mqclient.route;

import javax.annotation.Resource;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component("orderRoute1")
public class OrderRoute1 extends RouteBuilder {

	private static final String MQ_ENDPOINT = "jms:queue:bookStoreOrderInbound?transacted=true&replyTo=bookStoreOrderOutbound&replyToType=Exclusive&requestTimeout=10s";
	private static final String ENDPOINT = "direct:order1";

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
//		.process(orderRespProcessor)
//		.log("get processed billing meta data ${body}")
		.end();
}

}
