package com.hadrion.identidadeacesso.infraestrutura.persistencia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.identidadeacesso.dominio.acesso.papel.Papel;
import com.hadrion.identidadeacesso.dominio.acesso.papel.PapelRepositorio;
import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;

@Repository
@Transactional
public class PapelRepositorioJpa implements PapelRepositorio{
	
	@Autowired
	private PapelRepositorioSpringData repositorio;
	
	@Override
	public void salvar(Papel papel) {
		repositorio.save(papel);
	}

	@Override
	public List<Papel> todosPapeis() {
		return repositorio.findAll();
	}

	@Override
	public void adicionar(Papel papel) {
		if (repositorio
				.findByHospedeIdAndNome(
						papel.hospedeId(),
						papel.nome()) != null)
			throw new DataIntegrityViolationException("Papel " + papel.nome() + " já existe.");
		salvar(papel);
	}

	@Override
	public Papel papelChamado(HospedeId hospedeId, String nomePapel) {
		return repositorio
			.findByHospedeIdAndNome(
					hospedeId,
					nomePapel);
	}

}
