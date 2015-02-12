package com.hadrion.identidadeacesso.dominio.identidade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.identidadeacesso.comum.Afirmacao;

@Service
public class AutenticacaoService extends Afirmacao {
	
	@Autowired
	private HospedeRepositorio hospedeRepositorio;
	
	@Autowired
	private EncriptacaoService encriptacaoService;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	public DescritorUsuario autenticar(HospedeId hospedeId, String username,
			String senha) {
		this.assertArgumentoNaoNulo(hospedeId, "HospedeId deve ser não nulo.");
		this.assertArgumentoNaoNulo(username, "Username deve ser não nulo.");
		this.assertArgumentoNaoNulo(senha, "Senha deve ser não nula.");
		
		DescritorUsuario descritor = DescritorUsuario.intanciaDescritorNula();
		
		Hospede hospede = hospedeRepositorio.hospedeDoId(hospedeId);

		if (hospede != null && hospede.estaAtivo()) {
			String senhaEncriptada = encriptacaoService.valorEncriptado(senha);

			Usuario usuario = usuarioRepositorio.usuarioPelasCrendenciaisAutenticacao(
					hospedeId, username, senhaEncriptada);

			if (usuario != null && usuario.estaHabilitado()) {
				descritor = usuario.paraDescritorUsuario();
			}
		}
		
		return descritor;
	}

}
