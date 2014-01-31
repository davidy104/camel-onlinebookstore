package co.nz.onlinebookstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import co.nz.onlinebookstore.data.support.InitialDataSetup;

@Configuration
public class TestDataContextConfiguration {

	@Autowired
	PlatformTransactionManager transactionManager;

	@Bean(initMethod = "initialize")
	InitialDataSetup setupData() {
		return new InitialDataSetup(new TransactionTemplate(
				this.transactionManager));
	}
}
