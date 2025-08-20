package rest.productsmanager;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {

	public static void main(String[] args) {
		try {
			URI uri = UriBuilder.fromUri("http://localhost").port(8080).build();
			ResourceConfig config = new ResourceConfig();
			config.packages("rest.productsmanager");
			HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri,config);
			System.out.println("Rodando server de teste..." + server);
		} catch (Exception e) {
			System.out.println("Erro");

		}
	}

}
