package com.hadrion.identidadeacesso.dominio.identidade;

import java.util.List;

public interface HospedeRepositorio {
	
	HospedeId proximaIdentidade();

	void salvar(Hospede hospede);

	Hospede hospedeDoId(HospedeId hospedeId);

	List<Hospede> todos();

}
