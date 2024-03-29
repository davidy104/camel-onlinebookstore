package co.nz.onlinebookstore.mqclient.config;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.spring.CamelBeanPostProcessor;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.JmsTransactionManager;

import co.nz.onlinebookstore.mqclient.route.OrderRoute;

@Configuration
@PropertySource("classpath:activitymq-config.properties")
public class CamelMqConfig {

	@Autowired
	private PooledConnectionFactory pooledConnectionFactory;

	@Autowired
	private JmsComponent jmsComponent;

	@Resource
	private Environment environment;

	@Inject
	private ApplicationContext context;

	@Resource
	private OrderRoute orderRoute;

	private static final String ACTIVITYMQ_URL = "activitymq_url";
	private static final String ACTIVITYMQ_TRANSACTED = "activitymq_transacted";
	private static final String ACTIVITYMQ_MAXCONNECTIONS = "activitymq_maxConnections";
	private static final String ACTIVITYMQ_SENDTIMEOUT = "activitymq_sendTimeoutInMillis";
	private static final String ACTIVITYMQ_WATCHTOPICADVISORIES = "activitymq_watchTopicAdvisories";

	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				environment.getRequiredProperty(ACTIVITYMQ_URL));
		connectionFactory.setSendTimeout(Integer.valueOf(environment
				.getRequiredProperty(ACTIVITYMQ_SENDTIMEOUT)));
		connectionFactory.setMaxThreadPoolSize(5);
		connectionFactory.setWatchTopicAdvisories(Boolean.valueOf(environment
				.getRequiredProperty(ACTIVITYMQ_WATCHTOPICADVISORIES)));
		connectionFactory.setUseDedicatedTaskRunner(false);
		return connectionFactory;
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public PooledConnectionFactory pooledConnectionFactory() {
		PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
		pooledConnectionFactory.setMaxConnections(Integer.valueOf(environment
				.getRequiredProperty(ACTIVITYMQ_MAXCONNECTIONS)));
		pooledConnectionFactory
				.setConnectionFactory(activeMQConnectionFactory());
		return pooledConnectionFactory;
	}

	@Bean
	public JmsComponent jmsComponent() {
		JmsComponent jmsComponent = new JmsComponent();
		JmsConfiguration jmsConfiguration = new JmsConfiguration();
		jmsConfiguration.setConnectionFactory(pooledConnectionFactory);
		jmsConfiguration.setTransactionManager(jmsTransactionManager());
		jmsConfiguration.setTransacted(Boolean.valueOf(environment
				.getRequiredProperty(ACTIVITYMQ_TRANSACTED)));
		jmsConfiguration.setCacheLevelName("CACHE_CONSUMER");
		jmsComponent.setConfiguration(jmsConfiguration);
		return jmsComponent;
	}

	@Bean
	public JmsTransactionManager jmsTransactionManager() {
		JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
		jmsTransactionManager.setConnectionFactory(pooledConnectionFactory);
		return jmsTransactionManager;
	}

	@Bean
	public CamelBeanPostProcessor camelBeanPostProcessor() {
		CamelBeanPostProcessor camelBeanPostProcessor = new CamelBeanPostProcessor();
		camelBeanPostProcessor.setApplicationContext(context);
		return camelBeanPostProcessor;
	}

	@Bean
	public CamelContext camelContext() throws Exception {
		SpringCamelContext camelContext = new SpringCamelContext(context);
		camelContext.addComponent("jms", jmsComponent());
		camelContext.addRoutes(orderRoute);
		return camelContext;
	}

}
