package com.hadrion.identidadeacesso.infraestrutura.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hadrion.identidadeacesso.dominio.identidade.Hospede;
import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;

public interface HospedeRepositorioSpringData extends JpaRepository<Hospede, Long>{

	Hospede findByHospedeId(HospedeId hospedeId);
	
	
	
}
