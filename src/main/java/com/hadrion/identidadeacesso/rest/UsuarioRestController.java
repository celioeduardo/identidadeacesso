package com.hadrion.identidadeacesso.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hadrion.identidadeacesso.aplicacao.IdentidadeAcessoAplicacaoService;
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
	private IdentidadeAcessoAplicacaoService servico;
	
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
		
		//return new UsuarioRecurso(usuario);
		
		return usuarioResourceAssembler.toResource(usuario);
		
	}
	
//	@RequestMapping(method=RequestMethod.GET)
//	public Resources<HospedeRecurso> usuarios(){
//		Resources<HospedeRecurso> resources =  
//				new Resources<HospedeRecurso>(hospedeResourceAssembler.toResources(servico.hospedes()));
//		resources.add(links.linkToCollectionResource(HospedeData.class));
//		return resources;
//	}
	
}
