package co.nz.onlinebookstore.support

import groovy.util.logging.Slf4j

import org.springframework.stereotype.Component
@Slf4j
@Component
class OrderExceptionHandle {
	void handle(Exception exception) {
		log.info "OrderExceptionHandle start:{} $exception"
	}
}
