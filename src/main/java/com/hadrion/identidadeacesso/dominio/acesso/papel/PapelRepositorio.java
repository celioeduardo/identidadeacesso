package com.hadrion.identidadeacesso.dominio.acesso.papel;

import java.util.List;

import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;

public interface PapelRepositorio {

	void salvar(Papel papel);
	
	void adicionar(Papel papel);

	List<Papel> todosPapeis();

	Papel papelChamado(HospedeId hospedeId, String nomePapel);

}
