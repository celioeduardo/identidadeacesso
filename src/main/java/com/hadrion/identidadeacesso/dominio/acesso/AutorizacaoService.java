package com.hadrion.identidadeacesso.dominio.acesso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.identidadeacesso.comum.Afirmacao;
import com.hadrion.identidadeacesso.dominio.acesso.papel.Papel;
import com.hadrion.identidadeacesso.dominio.acesso.papel.PapelRepositorio;
import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;
import com.hadrion.identidadeacesso.dominio.identidade.Usuario;
import com.hadrion.identidadeacesso.dominio.identidade.UsuarioRepositorio;

@Service
public class AutorizacaoService extends Afirmacao{
	
	@Autowired
	private PapelRepositorio papelRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	public boolean usuarioEstaNoPapel(Usuario usuario, String nomePapel) {
		this.assertArgumentoNaoNulo(usuario, "Usuário não pode ser nulo.");
        this.assertArgumentoNaoVazio(nomePapel, "Nome do papel não pode ser nulo.");

        boolean autorizado = false;

        if (usuario.estaHabilitado()) {
            Papel papel = papelRepositorio.papelChamado(usuario.hospedeId(), nomePapel);

            if (papel != null) 
            	autorizado = papel.estaNoPapel(usuario);
        }

        return autorizado;
	}

	public boolean usuarioEstaNoPapel(HospedeId hospedeId, String username,
			String nomePapel) {
		this.assertArgumentoNaoNulo(hospedeId, "HospedeId não pode ser nulo.");
		this.assertArgumentoNaoVazio(username, "Username não pode ser nulo.");
        this.assertArgumentoNaoVazio(nomePapel, "Nome do papel não pode ser nulo.");
        
        Usuario usuario = usuarioRepositorio.usuarioComUsername(hospedeId, username);
        
		return usuario == null ? false : usuarioEstaNoPapel(usuario, nomePapel);
	}

}
