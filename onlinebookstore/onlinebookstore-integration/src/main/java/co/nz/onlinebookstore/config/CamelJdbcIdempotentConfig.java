package co.nz.onlinebookstore.config;

import javax.annotation.Resource;

import org.apache.camel.component.sql.SqlComponent;
import org.apache.camel.processor.idempotent.jdbc.JdbcMessageIdRepository;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author david JdbcIdempotent is only for order process, here we use JTA
 *         transaction
 */
@Configuration
public class CamelJdbcIdempotentConfig {

	@Resource
	private JdbcDataSource xaJdbcDataSource;

	@Resource
	private JtaTransactionManager jtaTransactionManager;

	@Bean
	public SqlComponent sqlComponent() {
		SqlComponent sqlComponent = new SqlComponent();
		sqlComponent.setDataSource(xaJdbcDataSource);
		return sqlComponent;
	}

	@Bean
	public JdbcMessageIdRepository orderJdbcMessageIdRepository() {
		TransactionTemplate transactionTemplate = new TransactionTemplate(
				jtaTransactionManager);
		JdbcMessageIdRepository jdbcMessageIdRepository = new JdbcMessageIdRepository(
				xaJdbcDataSource, transactionTemplate, "orderMqConsumer");

		jdbcMessageIdRepository
				.setTableExistsString("SELECT 1 FROM ORDER_REQ_MESSAGE WHERE 1 = 0");
		// jdbcMessageIdRepository
		// .setCreateString("CREATE TABLE ORDER_REQ_MESSAGE (processorName VARCHAR(255), messageId VARCHAR(100), createdAt TIMESTAMP)");
		jdbcMessageIdRepository
				.setDeleteString("DELETE FROM ORDER_REQ_MESSAGE WHERE processor_name = ? AND message_id = ?");
		jdbcMessageIdRepository
				.setQueryString("SELECT COUNT(*) FROM ORDER_REQ_MESSAGE WHERE processor_name = ? AND message_id = ? AND ORDER_ID is not null");
		jdbcMessageIdRepository
				.setInsertString("INSERT INTO ORDER_REQ_MESSAGE (processor_name, message_id, created_at) VALUES (?, ?, ?)");

		return jdbcMessageIdRepository;
	}
}
