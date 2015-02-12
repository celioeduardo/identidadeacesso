package com.hadrion.identidadeacesso.dominio.identidade;

public interface UsuarioRepositorio {

	void salvar(Usuario usuario);

	Usuario usuarioPelasCrendenciaisAutenticacao(HospedeId hospedeId,
			String username, String senhaEncriptada);

}
