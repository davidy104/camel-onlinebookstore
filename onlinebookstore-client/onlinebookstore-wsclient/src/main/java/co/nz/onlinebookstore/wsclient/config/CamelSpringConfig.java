package co.nz.onlinebookstore.wsclient.config;

import javax.inject.Inject;

import org.apache.camel.CamelContext;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.spring.CamelBeanPostProcessor;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelSpringConfig {

	@Inject
	private ApplicationContext context;

	@Autowired
	private CxfEndpoint bookstoreEndpoint;

	@Bean
	public CamelBeanPostProcessor camelBeanPostProcessor() {
		CamelBeanPostProcessor camelBeanPostProcessor = new CamelBeanPostProcessor();
		camelBeanPostProcessor.setApplicationContext(context);
		return camelBeanPostProcessor;
	}

	@Bean
	public CamelContext camelContext() throws Exception {
		SpringCamelContext camelContext = new SpringCamelContext(context);
		SimpleRegistry registry = new SimpleRegistry();
		registry.put("bookstoreEndpoint", bookstoreEndpoint);
		camelContext.setRegistry(registry);
		return camelContext;
	}

}
