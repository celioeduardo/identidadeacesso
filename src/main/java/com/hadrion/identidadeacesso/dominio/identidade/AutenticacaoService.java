package com.hadrion.identidadeacesso.dominio.identidade;


public interface AutenticacaoService {
	
	DescritorUsuario autenticar(HospedeId hospedeId, String username,
			String senha);

}
