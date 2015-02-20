package com.hadrion.identidadeacesso.aplicacao.data;

public class DescritorUsuarioData {
	
	private String hospedeId;
	private String username;
	private String email;
	
	public DescritorUsuarioData(String hospedeId, String username, String email) {
		super();
		this.hospedeId = hospedeId;
		this.username = username;
		this.email = email;
	}

	public String getHospedeId() {
		return hospedeId;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}
	
	

}
