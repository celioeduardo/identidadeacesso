package com.hadrion.identidadeacesso.infraestrutura.persistencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;
import com.hadrion.identidadeacesso.dominio.identidade.Usuario;
import com.hadrion.identidadeacesso.dominio.identidade.UsuarioRepositorio;

@Repository
@Transactional
public class UsuarioRepositorioJpa implements UsuarioRepositorio{
	
	@Autowired
	private UsuarioRepositorioSpringData repositorio;
	
	@Override
	public void salvar(Usuario usuario) {
		repositorio.save(usuario);
	}

	@Override
	public Usuario usuarioPelasCrendenciaisAutenticacao(HospedeId hospedeId,
			String username, String senhaEncriptada) {
		return repositorio.findByHospedeIdAndUsernameAndSenha(hospedeId,username,senhaEncriptada);
	}

	@Override
	public Usuario usuarioComUsername(HospedeId hospedeId, String username) {
		return repositorio.findByHospedeIdAndUsername(hospedeId,username);
	}

}
