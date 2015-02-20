package com.hadrion.identidadeacesso.rest;

import org.springframework.hateoas.ResourceSupport;

public class UsuarioPapelRecurso extends ResourceSupport {
	
	private String username;
	private String papel;
	
	public UsuarioPapelRecurso(String username, String papel) {
		super();
		this.username = username;
		this.papel = papel;
	}
	public String getUsername() {
		return username;
	}
	public String getPapel() {
		return papel;
	}
	
	
}
