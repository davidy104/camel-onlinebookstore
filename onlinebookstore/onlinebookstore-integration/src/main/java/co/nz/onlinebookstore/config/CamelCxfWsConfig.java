package co.nz.onlinebookstore.config;

import org.apache.camel.component.cxf.CxfEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import co.nz.onlinebookstore.ws.OnlineBookStoreWS;

@Configuration
@ImportResource({"classpath:META-INF/cxf/cxf.xml",
		"classpath:META-INF/cxf/cxf-extension-soap.xml",
		"classpath:META-INF/cxf/cxf-servlet.xml"})
public class CamelCxfWsConfig {

	@Bean
	public CxfEndpoint onlinebookstoreWsEndpoint() {
		CxfEndpoint cxfEndpoint = new CxfEndpoint();
		cxfEndpoint.setAddress("/generalWs");
		cxfEndpoint.setServiceClass(OnlineBookStoreWS.class);
		return cxfEndpoint;
	}
}
