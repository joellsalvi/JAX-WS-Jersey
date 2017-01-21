package br.com.alura.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {

	static HttpServer initServer() {
		URI uri = URI.create("http://localhost:8080/");
		ResourceConfig configuration = new ResourceConfig().packages("br.com.alura.loja.resource");
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, configuration);
		return server;
	}

	static void closeServer(HttpServer server) {
		server.stop();
	}
	
	public static void main(String[] args) throws IOException {
		HttpServer server = initServer();
		System.out.println("Servidor iniciado");
		System.in.read();
		closeServer(server);
	}

}
