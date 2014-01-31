package co.nz.onlinebookstore.mqclient;

import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.camel.CamelContext;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.nz.onlinebookstore.mqclient.config.MqAppconfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MqAppconfig.class)
public class OrderTest {
	@Produce
	private ProducerTemplate producer;

	@Resource
	private CamelContext camelContext;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderTest.class);

	@Test
	public void testPlaceOrder() throws Exception {
		String xmlReq = OrderTestUtils.orderXmlReq();
		// ProducerTemplate producer = camelContext.createProducerTemplate();

		// service side has duplicated check logic by using messageId
		String messageId = String.valueOf(getRandomNumber(5));
		String respMsg = producer.requestBodyAndHeader("direct:order", xmlReq,
				"messageId", messageId, String.class);
		// Thread.sleep(5000);

		// ConsumerTemplate consumer = camelContext.createConsumerTemplate();
		// String respMsg = consumer.receiveBody(
		// "jms:queue:bookStoreOrderOutbound?jmsMessageType=Text",
		// String.class);
		LOGGER.info("respmsg$$$$$$$$$$$$$$$$:{}", respMsg);
	}

	public static BigInteger getRandomNumber(final int digCount) {
		return getRandomNumber(digCount, new Random());
	}

	public static BigInteger getRandomNumber(final int digCount, Random rnd) {
		final char[] ch = new char[digCount];
		for (int i = 0; i < digCount; i++) {
			ch[i] = (char) ('0' + (i == 0 ? rnd.nextInt(9) + 1 : rnd
					.nextInt(10)));
		}
		return new BigInteger(new String(ch));
	}
}
