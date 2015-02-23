package com.hadrion.identidadeacesso.rest;

import org.springframework.hateoas.ResourceSupport;

public class UsuarioPapelRecurso extends ResourceSupport {
	
	private String hospedeId;
	private String username;
	private String papel;
	
	public UsuarioPapelRecurso(String hospedeId,
			String username, String papel) {
		super();
		this.hospedeId = hospedeId;
		this.username = username;
		this.papel = papel;
	}
	public String getUsername() {
		return username;
	}
	public String getPapel() {
		return papel;
	}
	public String getHospedeId() {
		return hospedeId;
	}
	
}
