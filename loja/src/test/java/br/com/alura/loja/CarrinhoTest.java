package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

public class CarrinhoTest {
	
	private static final String CARRINHOS_SERVICE = "/carrinhos";
	private HttpServer server;
	private Client client;
	private WebTarget target;

	@Before
	public void startServer() {
		this.server = Servidor.initServer();
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFilter());//Imprime log de propriedades da requisição - target - cabeçalho - etc
		this.client = ClientBuilder.newClient(config);
		this.target = this.client.target("http://localhost:8080");
	}
	
	@After
	public void stopServer() {
		Servidor.closeServer(this.server);
	}
	
	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {

//		String response = this.target.path("/carrinho/1").request().get(String.class);
//		Carrinho carrinho = new Gson().fromJson(response, Carrinho.class);

		Carrinho carrinho = this.target.path(CARRINHOS_SERVICE+"/1").request().get(Carrinho.class);
		
		Assert.assertTrue("Rua Vergueiro 3185, 8 andar".equalsIgnoreCase(carrinho.getRua()));
		
	}
	
	@Test
	public void adicionarCarrinhoViaPost() {
		
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
//        String json = carrinho.toJson();
//        Entity<String> entity = Entity.entity(json, MediaType.APPLICATION_JSON);
        Entity<Carrinho> entity = Entity.entity(carrinho, MediaType.APPLICATION_XML);

        Response response = this.target.path(CARRINHOS_SERVICE).request().post(entity);
        Assert.assertTrue(201 == response.getStatus());//201 = Status Response Created
        
        String location = response.getHeaderString("Location");
        Carrinho carrinhoInserido = this.client.target(location).request().get(Carrinho.class);
        Assert.assertTrue(carrinhoInserido.getProdutos().get(0).getNome().equals("Tablet"));
	}

}
