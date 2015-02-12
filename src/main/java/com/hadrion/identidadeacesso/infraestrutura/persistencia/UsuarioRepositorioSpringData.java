package com.hadrion.identidadeacesso.infraestrutura.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;
import com.hadrion.identidadeacesso.dominio.identidade.Usuario;

public interface UsuarioRepositorioSpringData extends JpaRepository<Usuario, Long>{

	Usuario findByHospedeIdAndUsernameAndSenha(HospedeId hospedeId,
			String username, String senhaEncriptada);

}
