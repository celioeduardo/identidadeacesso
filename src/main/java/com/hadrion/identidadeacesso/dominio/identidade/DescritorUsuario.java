package com.hadrion.identidadeacesso.dominio.identidade;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class DescritorUsuario {
	
	private HospedeId hospedeId;
	private String username;
	private String email;
	
	public DescritorUsuario(HospedeId hospedeId, String username, String email) {
		super();
		this.hospedeId = hospedeId;
		this.username = username;
		this.email = email;
	}
	
	private DescritorUsuario() {}
	
	public HospedeId hospedeId(){
		return hospedeId;
	}
	
	public String username(){
		return username;
	}
	
	public String email(){
		return email;
	}
	
	public boolean ehDescritorNulo() {
        return this.email() == null || this.hospedeId() == null || this.username() == null;
    }
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			DescritorUsuario objetoTipado = (DescritorUsuario) objeto;
			objetosIguais = new EqualsBuilder()
				.append(hospedeId(),objetoTipado.hospedeId())
				.append(username(),objetoTipado.username())
				.append(email(),objetoTipado.email())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(233,99)
			.append(hospedeId())
			.append(username())
			.append(email())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "DescritorUsuario [hospedeId=" + hospedeId()
				+ ", username=" + username()
				+ ", email=" + email()
				+ "]";
	}

	public static DescritorUsuario intanciaDescritorNula() {
		return new DescritorUsuario();
	}
	
}
