package com.hadrion.identidadeacesso.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hadrion.identidadeacesso.aplicacao.IdentidadeAplicacaoService;
import com.hadrion.identidadeacesso.aplicacao.comando.AlocarHospedeComando;
import com.hadrion.identidadeacesso.aplicacao.data.HospedeData;


@RestController
@RequestMapping(
	value="/hospedes",
	produces={MediaType.APPLICATION_JSON_VALUE,IdentidadeAcessoMediaType.ID_HADRION_TYPE})
@ExposesResourceFor(HospedeData.class)
public class HospedeRestController {
	
	@Autowired EntityLinks links;
	
	@Autowired
	private IdentidadeAplicacaoService servico;
	
	@Autowired
	private HospedeResourceAssembler hospedeResourceAssembler;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public HospedeRecurso obter(@PathVariable String id){
		HospedeData hospede = servico.hospede(id);
		
		if (hospede == null)
			throw new RecursoNaoEncontadoException("Hospede ["+id+"] não encontrado.");
		
		HospedeRecurso recurso = hospedeResourceAssembler.toResource(hospede);
		return recurso;
		
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public Resources<HospedeRecurso> hospedes(){
		
//		List<HospedeRecurso> hospedes = servico.hospedes()
//				.stream()
//				.map(HospedeRecurso::new)
//				.collect(Collectors.toList());
//		
//		return new Resources<HospedeRecurso>(hospedes);
		Resources<HospedeRecurso> resources =  
				new Resources<HospedeRecurso>(hospedeResourceAssembler.toResources(servico.hospedes()));
		//resources.add(links.linkToCollectionResource(HospedeData.class));
		return resources;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> alocar(
			@RequestBody AlocarHospedeComando comando){
		
		HospedeData hospede = servico.alocarHospede(comando);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		Link paraUmHospede = hospedeResourceAssembler
				.toResource(hospede).getLink("self");
		httpHeaders.setLocation(URI.create(paraUmHospede.getHref()));
		return new ResponseEntity<>(httpHeaders,HttpStatus.CREATED);
		
	}
	
}
