package br.com.alura.loja.resource;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;

@Path(value="projetos")
public class ProjetoResource {
	
	@POST
	@Produces(value=MediaType.APPLICATION_XML)
	@Consumes("application/x-www-form-urlencoded")
	public String buscaPorId(MultivaluedMap<String, String> formParams) {
		long id = Long.parseLong(formParams.getFirst("id"), 10);
		return new ProjetoDAO().busca(id).toXML();
	}
	
	@GET
	@Produces(value=MediaType.APPLICATION_XML)
	public String findAll() {
		Collection<Projeto> projetos = new ProjetoDAO().buscaTodos();
		return new XStream().toXML(projetos);
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_XML)
	public String findByIdOnPath(@PathParam("id") long id) {
		return new ProjetoDAO().busca(id).toXML(); 
	}
	
	@POST
	@Produces(MediaType.APPLICATION_XML)
	public String adiciona(String strProjeto) {
		new ProjetoDAO().adiciona((Projeto) new XStream().fromXML(strProjeto));
		return "<status>sucesso</status>";
	}
	
}
