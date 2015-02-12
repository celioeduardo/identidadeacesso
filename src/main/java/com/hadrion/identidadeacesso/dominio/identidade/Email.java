package com.hadrion.identidadeacesso.dominio.identidade;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.hadrion.identidadeacesso.comum.Afirmacao;

@Embeddable
@Access(AccessType.FIELD)
public class Email extends Afirmacao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="EMAIL")
	private String endereco;

	public Email(String endereco) {
		super();

		this.setEndereco(endereco);
	}

	public Email(Email email) {
		this(email.endereco());
	}

	public String endereco() {
		return this.endereco;
	}

	@Override
	public boolean equals(Object anObject) {
		boolean equalObjects = false;

		if (anObject != null && this.getClass() == anObject.getClass()) {
			Email typedObject = (Email) anObject;
			equalObjects = this.endereco().equals(typedObject.endereco());
		}

		return equalObjects;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = +(17861 * 179) + this.endereco().hashCode();

		return hashCodeValue;
	}

	@Override
	public String toString() {
		return endereco;
	}

	protected Email() {
		super();
	}

	private void setEndereco(String endereco) {
		this.assertArgumentoNaoVazio(endereco, "O endereço de e-mail é obrigatório.");
		this.assertTamanhoArgumento(endereco, 1, 100,
				"Endereço de email deve ter 100 caracteres ou menos.");
		this.assertArgumentoVerdadeiro(Pattern.matches(
				"\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*",
				endereco), "Email address format is invalid.");

		this.endereco = endereco;
	}
}
