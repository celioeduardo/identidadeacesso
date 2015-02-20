package com.hadrion.identidadeacesso.dominio.identidade.grupo;

import java.util.List;

import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;

public interface GrupoRepositorio {

	void adicionar(Grupo grupo);

	List<Grupo> todosGrupos(HospedeId hospedeId);

	Grupo grupoChamado(HospedeId hospedeId, String nome);

}
