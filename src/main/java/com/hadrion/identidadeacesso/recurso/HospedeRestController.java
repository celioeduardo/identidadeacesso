package com.hadrion.identidadeacesso.recurso;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hadrion.identidadeacesso.aplicacao.IdentidadeAcessoAplicacaoService;
import com.hadrion.identidadeacesso.aplicacao.comando.AlocarHospedeComando;
import com.hadrion.identidadeacesso.aplicacao.data.HospedeData;


@RestController
@RequestMapping("/hospedes")
@ExposesResourceFor(HospedeData.class)
public class HospedeRestController {

	@Autowired
	private IdentidadeAcessoAplicacaoService servico;
	
	@Autowired
	private HospedeResourceAssembler hospedeResourceAssembler;
	
	@RequestMapping(value="/{hospedeId}",
			method=RequestMethod.GET, 
			produces=IdentidadeAcessoMediaType.ID_HADRION_TYPE)
	public HospedeRecurso obter(@PathVariable String hospedeId){
		HospedeData hospede = servico.hospede(hospedeId);
		
		if (hospede == null)
			throw new RecursoNaoEncontadoException("Hospede ["+hospedeId+"] não encontrado.");
		
		HospedeRecurso recurso = hospedeResourceAssembler.toResource(hospede);
		return recurso;
		
	}
	
	@RequestMapping(method=RequestMethod.GET, 
			produces=IdentidadeAcessoMediaType.ID_HADRION_TYPE)
	public List<HospedeRecurso> hospedes(){
		return hospedeResourceAssembler.toResources(servico.hospedes());
	}
	
	@RequestMapping(method=RequestMethod.POST, 
			produces=IdentidadeAcessoMediaType.ID_HADRION_TYPE)
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
