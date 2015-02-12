package com.hadrion.identidadeacesso.dominio.identidade;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.identidadeacesso.comum.Afirmacao;

@Embeddable
@Access(AccessType.FIELD)
public class InformacaoContato extends Afirmacao{
	
	@Embedded
	private Email email;

	public InformacaoContato(Email email) {
		super();
		this.setEmail(email);
	}
	
	@SuppressWarnings("unused")
	private InformacaoContato(){}
	
	public Email email(){
		return email;
	}
	
	public InformacaoContato alterarEmail(Email email){
		return new InformacaoContato(email);
	}
	
	private void setEmail(Email email){
		this.assertArgumentoNaoNulo(email, "O endereço de e-mail é obrigatório");
		this.email = email;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			InformacaoContato objetoTipado = (InformacaoContato) objeto;
			objetosIguais = new EqualsBuilder()
				.append(email,objetoTipado.email)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(3341,457)
			.append(email)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "InformacaoContato [email=" + email
				+ "]";
	}	
	
}
