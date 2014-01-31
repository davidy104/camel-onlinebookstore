package co.nz.onlinebookstore.mqclient.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderRespProcessor implements Processor {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderRespProcessor.class);
	@Override
	public void process(Exchange exchange) throws Exception {
		LOGGER.info("************************************OrderRespProcessor start************************:{} ");
		String inBody = exchange.getIn().getBody(String.class);
		String outBody = exchange.getOut().getBody(String.class);

		LOGGER.info("inBody:{} ", inBody);
		LOGGER.info("outBody:{} ", outBody);
	}

}
