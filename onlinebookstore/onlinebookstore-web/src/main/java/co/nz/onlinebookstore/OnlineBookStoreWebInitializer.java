package co.nz.onlinebookstore;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import co.nz.onlinebookstore.config.ApplicationConfiguration;
import co.nz.onlinebookstore.config.JerseySpringServlet;

public class OnlineBookStoreWebInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		// Loading application context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ApplicationConfiguration.class);

		ServletRegistration.Dynamic cxfServletDispatcher = servletContext
				.addServlet("CXFServlet", CXFServlet.class);
		cxfServletDispatcher.addMapping("/ws/*");

		ServletRegistration.Dynamic jerseyServletDispatcher = servletContext
				.addServlet("JerseySpringServlet", JerseySpringServlet.class);
		jerseyServletDispatcher.addMapping("/rest/*");

		// Context loader listener
		servletContext.addListener(new ContextLoaderListener(rootContext));
	}
}
