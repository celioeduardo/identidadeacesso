package com.hadrion.identidadeacesso.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.hadrion.identidadeacesso.aplicacao.data.HospedeData;

@Component
public class HospedeResourceAssembler extends ResourceAssemblerSupport<HospedeData, HospedeRecurso> {

	public HospedeResourceAssembler() {
		super(HospedeRestController.class, HospedeRecurso.class);
	}

	@Override
	public HospedeRecurso toResource(HospedeData hospede) {
		HospedeRecurso recurso = createResourceWithId(hospede.getHospedeId(),hospede);
		recurso.add(linkTo(HospedeRestController.class).withRel("hospedes"));
		return recurso;
	}
	
	@Override
    protected HospedeRecurso instantiateResource(HospedeData hospede) {
        return new HospedeRecurso(hospede);
    }

}
