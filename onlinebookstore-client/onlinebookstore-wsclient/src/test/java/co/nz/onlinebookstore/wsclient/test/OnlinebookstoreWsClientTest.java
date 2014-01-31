package co.nz.onlinebookstore.wsclient.test;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.nz.onlinebookstore.wsclient.config.OnlinebookstoreWsClientConfig;
import co.nz.onlinebookstore.wsclient.stub.BookDto;
import co.nz.onlinebookstore.wsclient.stub.FaultMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {OnlinebookstoreWsClientConfig.class})
public class OnlinebookstoreWsClientTest {

	@Produce
	private ProducerTemplate template;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OnlinebookstoreWsClientTest.class);

	@Test
	public void testGetBookByName() {
		String bookName = "Camel in action";
		BookDto book = null;
		// MessageContentsList result = (MessageContentsList) template
		// .sendBodyAndHeader(
		// "cxf:bean:bookstoreEndpoint?dataFormat=POJO",
		// ExchangePattern.InOut, bookName,
		// CxfConstants.OPERATION_NAME, "getBookByName");
		//
		// LOGGER.info("result:{}", result);
		// book = (BookDto) (result.get(0));
		// LOGGER.info("bookName:{}", book.getBookName());
		// LOGGER.info("bookIsbn:{}", book.getIsbn());
		// LOGGER.info("author:{}", book.getAuthor());

		book = template.requestBodyAndHeader(
				"cxf:bean:bookstoreEndpoint?dataFormat=POJO", bookName,
				CxfConstants.OPERATION_NAME, "getBookByName", BookDto.class);
		LOGGER.info("bookName:{}", book.getBookName());
		LOGGER.info("bookIsbn:{}", book.getIsbn());
		LOGGER.info("author:{}", book.getAuthor());
	}

	@Test
	public void testGetNotExistedBookByName() {
		String bookName = "Camel cookbook";
		BookDto book = null;

		try {
			book = template
					.requestBodyAndHeader(
							"cxf:bean:bookstoreEndpoint?dataFormat=POJO",
							bookName, CxfConstants.OPERATION_NAME,
							"getBookByName", BookDto.class);
		} catch (CamelExecutionException e) {
			FaultMessage fault = (FaultMessage) e.getCause();
			LOGGER.info("reason = {}", fault.getMessage());
		}
	}

}
