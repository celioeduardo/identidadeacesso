package com.hadrion.identidadeacesso.aplicacao.data;

public class DescritorUsuarioData {
	
	private String hospedeId;
	private String username;
	private String email;
	private boolean habilitado;
	
	public DescritorUsuarioData(String hospedeId, String username, 
			String email, boolean habilitado) {
		super();
		this.hospedeId = hospedeId;
		this.username = username;
		this.email = email;
		this.habilitado = habilitado;
	}

	public boolean isHabilitado() {
		return habilitado;
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
