package co.nz.onlinebookstore.test;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.nz.onlinebookstore.config.ApplicationConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@Ignore
public class OrderTest {

	@Produce(uri = "jms:queue:bookStoreOrderInbound?jmsMessageType=Text&transacted=true")
	private ProducerTemplate producer;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderTest.class);

	@EndpointInject(uri = "mock:duplicate")
	private MockEndpoint mockDuplicate;

	@Test
	public void testPlaceOrder() throws Exception {
		mockDuplicate.setExpectedMessageCount(1);

		String xmlReq = OrderTestUtils.orderXmlReq();
//		producer.sendBody(xmlReq);
		String msgId = "123456666";
		LOGGER.info("start first request with same msgId:{}$$$$$$$$$$$$$$$$$$$$");
		producer.sendBodyAndHeader(xmlReq, "messageId", msgId);
		Thread.sleep(5000);

		LOGGER.info("start second request with same msgId:{}*******************");
		producer.sendBodyAndHeader(xmlReq, "messageId", msgId);
		// String responseStr = consumer.receiveBody(
		// "jms:queue:bookStoreOrderOutbound", String.class);
		//
		// LOGGER.info("receive:{}", responseStr);
	}

}
