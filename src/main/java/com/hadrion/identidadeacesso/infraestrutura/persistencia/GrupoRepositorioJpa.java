package com.hadrion.identidadeacesso.infraestrutura.persistencia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;
import com.hadrion.identidadeacesso.dominio.identidade.grupo.Grupo;
import com.hadrion.identidadeacesso.dominio.identidade.grupo.GrupoRepositorio;

@Repository
@Transactional
public class GrupoRepositorioJpa implements GrupoRepositorio{
	
	@Autowired
	private GrupoRepositorioSpringData repositorio;
	
	
	@Override
	public void adicionar(Grupo grupo) {
		if (grupoChamado(grupo.hospedeId(),grupo.nome()) != null)
			throw new DataIntegrityViolationException("Grupo " + grupo.nome() + " já existe.");
		repositorio.save(grupo);
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<Grupo> todosGrupos(HospedeId hospedeId) {
		return repositorio.findByHospedeId(hospedeId);
	}

	@Override
	public Grupo grupoChamado(HospedeId hospedeId, String nome) {
		return repositorio.findByHospedeIdAndNome(hospedeId,nome);
	}
	

}
