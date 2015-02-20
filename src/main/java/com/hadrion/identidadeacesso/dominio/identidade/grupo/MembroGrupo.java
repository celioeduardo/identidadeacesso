package com.hadrion.identidadeacesso.dominio.identidade.grupo;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;

@Embeddable
@Access(AccessType.FIELD)
public class MembroGrupo {
	
	@Embedded
	private HospedeId hospedeId;
	
	@Column(name="NOME",nullable=false)
	private String nome;
	
	@Enumerated(EnumType.STRING)
	private TipoMembro tipo;

	public MembroGrupo(HospedeId hospedeId, String nome, TipoMembro tipo) {
		super();
		this.hospedeId = hospedeId;
		this.nome = nome;
		this.tipo = tipo;
	}
	
	@SuppressWarnings("unused")
	private MembroGrupo(){}
	
	public boolean ehGrupo(){
		return tipo.ehGrupo();
	}
	public boolean ehUsuario(){
		return tipo.ehUsuario();
	}
	
	public String nome(){
		return nome;
	}
	
	public HospedeId hospedeId(){
		return hospedeId;
	}
	
	private TipoMembro tipo(){
		return tipo;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			MembroGrupo objetoTipado = (MembroGrupo) objeto;
			objetosIguais = new EqualsBuilder()
				.append(hospedeId(),objetoTipado.hospedeId())
				.append(nome(),objetoTipado.nome())
				.append(tipo(),objetoTipado.tipo())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(335,77)
			.append(hospedeId())
			.append(nome())
			.append(tipo())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "MembroGrupo [hospedeId=" + hospedeId()
				+ ",nome=" + nome()
				+ ",tipo=" + tipo()
				+ "]";
	}
	
}
