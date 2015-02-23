package com.hadrion.identidadeacesso.rest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hadrion.identidadeacesso.aplicacao.AcessoAplicacaoService;
import com.hadrion.identidadeacesso.aplicacao.IdentidadeAplicacaoService;
import com.hadrion.identidadeacesso.aplicacao.data.DescritorUsuarioData;
import com.hadrion.identidadeacesso.aplicacao.data.HospedeData;


@RestController
@RequestMapping(
	value="/hospedes/{hospedeId}/usuarios",
	produces={MediaType.APPLICATION_JSON_VALUE,IdentidadeAcessoMediaType.ID_HADRION_TYPE})
@ExposesResourceFor(HospedeData.class)
public class UsuarioRestController {
	
	@Autowired EntityLinks links;
	
	@Autowired
	private IdentidadeAplicacaoService servico;
	
	@Autowired
	private AcessoAplicacaoService acessoAplicacaoService;
	
	@Autowired
	private UsuarioResourceAssembler usuarioResourceAssembler;
	
	@RequestMapping(value="{username}/autenticadoPor/{senha}",method=RequestMethod.GET)
	public UsuarioRecurso autenticadoPor(
			@PathVariable String hospedeId,
			@PathVariable String username,
			@PathVariable String senha){
		
		DescritorUsuarioData usuario = servico.autenticarUsuario(hospedeId,username,senha);
		
		if (usuario == null)
			throw new RecursoNaoEncontadoException("não encontrado.");
		
		return usuarioResourceAssembler.toResource(usuario);
		
	}
	
	@RequestMapping(value="{username}",method=RequestMethod.GET)
	public UsuarioRecurso obter(
			@PathVariable String hospedeId,
			@PathVariable String username){
		
		DescritorUsuarioData usuario = servico.obterUsuario(hospedeId,username);
		
		if (usuario == null)
			throw new RecursoNaoEncontadoException("não encontrado.");
		
		return usuarioResourceAssembler.toResource(usuario);
		
	}
	
	@RequestMapping(value="{username}/noPapel/{papel}",method=RequestMethod.GET)
	public UsuarioPapelRecurso usuarioNoPapel(
			@PathVariable String hospedeId,
			@PathVariable String username,
			@PathVariable String papel,
			HttpServletResponse response){
		
		boolean estaNoPapel = acessoAplicacaoService.usuarioEstaNoPapel(
				hospedeId,username,papel);
		
		if (estaNoPapel)
			return new UsuarioPapelRecurso(hospedeId,username, papel);
		else {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			return null;
		}
	}
	
//	@RequestMapping(method=RequestMethod.GET)
//	public Resources<HospedeRecurso> usuarios(){
//		Resources<HospedeRecurso> resources =  
//				new Resources<HospedeRecurso>(hospedeResourceAssembler.toResources(servico.hospedes()));
//		resources.add(links.linkToCollectionResource(HospedeData.class));
//		return resources;
//	}
	
}
