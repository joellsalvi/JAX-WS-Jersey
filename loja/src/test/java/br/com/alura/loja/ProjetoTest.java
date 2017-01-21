package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Projeto;

public class ProjetoTest {

	private static final String PROJETOS_SERVICE = "/projetos";
	private HttpServer server;

	@Before
	public void startServer() {
		server = Servidor.initServer();
	}
	
	@After
	public void stopServer() {
		Servidor.closeServer(server);
	}
	
	@Test
	public void testBuscaProjeto() {
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		
		Form form = new Form();
		form.param("id", "1");
		
		String conteudo = target.path(PROJETOS_SERVICE).request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
		
		Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
		
		System.out.println(projeto);
		
		Assert.assertTrue(projeto.getId() == 1l);
	}
	
}
