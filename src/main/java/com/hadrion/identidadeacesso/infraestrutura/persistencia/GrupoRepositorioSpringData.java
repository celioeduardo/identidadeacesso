package com.hadrion.identidadeacesso.infraestrutura.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;
import com.hadrion.identidadeacesso.dominio.identidade.grupo.Grupo;

public interface GrupoRepositorioSpringData extends JpaRepository<Grupo, Long>{

	List<Grupo> findByHospedeId(HospedeId hospedeId);
	
	Grupo findByHospedeIdAndNome(HospedeId hospedeId, String nome);


}
