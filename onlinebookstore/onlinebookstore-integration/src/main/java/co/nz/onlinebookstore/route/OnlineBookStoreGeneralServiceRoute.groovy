package co.nz.onlinebookstore.route

import javax.annotation.Resource

import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component

import co.nz.onlinebookstore.ConvertException
import co.nz.onlinebookstore.DuplicatedException
import co.nz.onlinebookstore.NotFoundException
import co.nz.onlinebookstore.service.BookService
import co.nz.onlinebookstore.ws.FaultHandler

@Component("onlineBookStoreGeneralServiceRoute")
class OnlineBookStoreGeneralServiceRoute extends RouteBuilder{

	static final String ENDPOINT = "cxf:bean:onlinebookstoreWsEndpoint"

	@Resource
	BookService bookService


	@Override
	void configure() {
		from(ENDPOINT).routeId(ENDPOINT)
				.onException(NotFoundException.class,ConvertException.class,DuplicatedException.class)
				.handled(true)
				.setFaultBody(method(FaultHandler.class, "createFault"))
				.end()
				.to("log:input")
				.choice()
				.when(header('operationName').isEqualTo('getBookByName'))
				.bean(bookService, "getBookByName")
				.endChoice()
				.otherwise()
				.throwException(
				new Exception("opeartion can not be identified"))
				.end()
				.to("log:output")
	}
}
