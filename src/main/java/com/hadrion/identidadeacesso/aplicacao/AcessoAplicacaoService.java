package com.hadrion.identidadeacesso.aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.identidadeacesso.dominio.acesso.AutorizacaoService;
import com.hadrion.identidadeacesso.dominio.acesso.papel.Papel;
import com.hadrion.identidadeacesso.dominio.acesso.papel.PapelRepositorio;
import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;
import com.hadrion.identidadeacesso.dominio.identidade.Usuario;
import com.hadrion.identidadeacesso.dominio.identidade.UsuarioRepositorio;

@Service
@Transactional
public class AcessoAplicacaoService {
	
	@Autowired
	private AutorizacaoService autorizacaoService;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private PapelRepositorio papelRepositorio;
	
	@Transactional(readOnly=true)
	public boolean usuarioEstaNoPapel(String hospedeId, String username,
			String papel) {
		
		return usuarioNoPapel(hospedeId, username, papel) != null;
	}
	
	private Usuario usuarioNoPapel(String hospedeId, String username,
			String nomePapel){
		
        Usuario usuario = usuarioRepositorio
    		.usuarioComUsername(
				new HospedeId(hospedeId), 
				username);
        
        if (usuario == null) 
        	return null;
        
        Papel papel = papelRepositorio
        	.papelChamado(new HospedeId(hospedeId), nomePapel);
        
        if (papel == null)
        	return null;
        
        if (papel.estaNoPapel(usuario))
        	return usuario;
        else 
        	return null;
        
	}
	
	
}
