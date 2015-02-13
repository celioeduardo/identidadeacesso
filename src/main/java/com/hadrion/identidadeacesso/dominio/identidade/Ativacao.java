package com.hadrion.identidadeacesso.dominio.identidade;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hadrion.identidadeacesso.comum.Afirmacao;

@Embeddable
@Access(AccessType.FIELD)
public class Ativacao extends Afirmacao{
	
	public static Ativacao ativacaoIndeterminada(){
		return new Ativacao(true,null,null);
	}
	
	@Column(name="HABILITADA")
	private boolean habilidata;
	
	@Column(name="DATA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;
	
	@Column(name="DATA_FIM")
    @Temporal(TemporalType.TIMESTAMP)
	private Date dataFim;

	public Ativacao(boolean habilitada, Date dataInicio, Date dataFim) {

		if (dataInicio != null || dataFim != null) {
			this.assertArgumentoNaoNulo(dataInicio,
					"A data início precisa ser fornecida.");
			this.assertArgumentoNaoNulo(dataFim,
					"A data fim precisa ser fornecida.");
			this.assertArgumentoFalso(dataInicio.after(dataFim),
					"Data de início e/ou fim de ativação é inválida");
		}

		this.habilidata = habilitada;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}

	@SuppressWarnings("unused")
	private Ativacao(){}
	
	public boolean estaHabilitada() {

		boolean habilidata = false;

		if (this.habilidata)
			if (!estaTempoExpirado())
				habilidata = true;

		return habilidata;
	}

	public boolean estaTempoExpirado() {
		boolean tempoExpirado = false;

		if (this.dataInicio() != null && this.dataFim() != null) {
			Date now = new Date();
			if (now.before(this.dataInicio()) || now.after(this.dataFim())) {
				tempoExpirado = true;
			}
		}

		return tempoExpirado;
	}

	public Date dataInicio() {
		return dataInicio;
	}

	public Date dataFim() {
		return dataFim;
	}

}
