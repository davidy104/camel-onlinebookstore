package co.nz.onlinebookstore.mqclient.msgListener

import groovy.util.logging.Slf4j

import org.apache.camel.Consume
import org.springframework.stereotype.Component

@Slf4j
//@Component
class OrderMessageConsumer {

	@Consume(uri = "jms:queue:bookStoreOrderOutbound")
	public String onMessage(String message) {
		log.info "Message:************************{} $message"
		return message
	}
}
