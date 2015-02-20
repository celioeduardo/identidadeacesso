package com.hadrion.identidadeacesso.infraestrutura.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hadrion.identidadeacesso.dominio.acesso.papel.Papel;
import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;

public interface PapelRepositorioSpringData extends JpaRepository<Papel, Long>{

	Papel findByHospedeIdAndNome(HospedeId hospedeId, String nome);
	
}
