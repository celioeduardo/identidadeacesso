package com.hadrion.identidadeacesso.rest;

import org.springframework.hateoas.ResourceSupport;

import com.hadrion.identidadeacesso.aplicacao.data.HospedeData;

public class HospedeRecurso extends ResourceSupport {
	
	private HospedeData hospede;
	
	public HospedeRecurso(HospedeData hospede) {
		super();
		this.hospede = hospede;
	}

	public HospedeData getHospede(){
		return hospede;
	}
	
}
