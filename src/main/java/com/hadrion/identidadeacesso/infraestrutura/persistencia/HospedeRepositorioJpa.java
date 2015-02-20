package com.hadrion.identidadeacesso.infraestrutura.persistencia;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.identidadeacesso.dominio.identidade.Hospede;
import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;
import com.hadrion.identidadeacesso.dominio.identidade.HospedeRepositorio;

@Repository
@Transactional
class HospedeRepositorioJpa implements HospedeRepositorio {
	
	@Autowired
	private HospedeRepositorioSpringData repositorio;
	
	@Transactional(readOnly=true)
	@Override
	public HospedeId proximaIdentidade() {
		return new HospedeId(UUID.randomUUID().toString().toUpperCase());
	}

	@Override
	public void salvar(Hospede hospede) {
		repositorio.save(hospede);
	}

	@Transactional(readOnly=true)
	@Override
	public Hospede hospedeDoId(HospedeId hospedeId) {
		return repositorio.findByHospedeId(hospedeId);
	}

	@Override
	public List<Hospede> todos() {
		return repositorio.findAll();
	}

}
