package co.nz.onlinebookstore.config;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = {"/rest/*"}, initParams = {
		@WebInitParam(name = "com.sun.jersey.config.property.packages", value = "co.nz.onlinebookstore"),
		@WebInitParam(name = "com.sun.jersey.api.json.POJOMappingFeature", value = "true")})
public class JerseySpringServlet extends SpringServlet {
}
