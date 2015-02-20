package com.hadrion.identidadeacesso.rest;

import org.springframework.hateoas.ResourceSupport;

import com.hadrion.identidadeacesso.aplicacao.data.DescritorUsuarioData;

public class UsuarioRecurso extends ResourceSupport {
	
	private DescritorUsuarioData usuario;
	
	public UsuarioRecurso(DescritorUsuarioData usuario) {
		super();
		this.usuario = usuario;
	}

	public DescritorUsuarioData getUsuario(){
		return usuario;
	}
	
}
