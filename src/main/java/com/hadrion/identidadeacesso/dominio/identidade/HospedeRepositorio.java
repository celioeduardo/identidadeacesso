package com.hadrion.identidadeacesso.dominio.identidade;

public interface HospedeRepositorio {
	
	HospedeId proximaIdentidade();

	void salvar(Hospede hospede);

	Hospede hospedeDoId(HospedeId hospedeId);

}
