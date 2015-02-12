package com.hadrion.identidadeacesso.dominio.identidade;

import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.hadrion.identidadeacesso.comum.AbstractId;

@Embeddable
@Access(AccessType.FIELD)
public class HospedeId extends AbstractId{

	private static final long serialVersionUID = 1L;
	
	@Column(name="HOSPEDE_ID")
	private String id;
	
	public HospedeId() {
		super();
	}

	public HospedeId(String id) {
		super(id);
	}

	@Override
    protected int hashOddValue() {
        return 83811;
    }

    @Override
    protected int hashPrimeValue() {
        return 263;
    }

    protected void validarId(String id) {
        try {
            UUID.fromString(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("O id tem um formato inválido.");
        }
    }

	@Override
	public String id() {
		return id;
	}

	@Override
	protected void setId(String id) {
        this.assertArgumentoNaoVazio(id, "A identidade é obrigatória.");
        this.assertTamanhoArgumento(id, 36, "A identidade precisa ter 36 caracteres.");

        this.validarId(id);

        this.id = id;
	}

}
