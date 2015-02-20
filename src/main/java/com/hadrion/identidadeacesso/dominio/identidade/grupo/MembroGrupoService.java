package com.hadrion.identidadeacesso.dominio.identidade.grupo;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.identidadeacesso.dominio.identidade.Usuario;
import com.hadrion.identidadeacesso.dominio.identidade.UsuarioRepositorio;

@Service
public class MembroGrupoService {
	
	@Autowired
	private GrupoRepositorio grupoRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	public boolean ehMembroDoGrupo(Grupo grupo, MembroGrupo membroGrupo) {
		boolean ehMembro = false;

		Iterator<MembroGrupo> iter = grupo.membros().iterator();

		while (!ehMembro && iter.hasNext()) {
			MembroGrupo membro = iter.next();
			if (membro.ehGrupo()) {
				if (membroGrupo.equals(membro)) {
					ehMembro = true;
				} else {
					Grupo g = grupoRepositorio.grupoChamado(
							membro.hospedeId(), membro.nome());
					if (g != null) {
						ehMembro = this.ehMembroDoGrupo(g, membroGrupo);
					}
				}
			}
		}

		return ehMembro;
	}

	public boolean confirmarUsuario(Grupo grupo, Usuario usuario) {
		boolean confirmado = true;

        Usuario usuarioConfirmado =
                usuarioRepositorio.usuarioComUsername(grupo.hospedeId(),usuario.username());
                    
        if (usuarioConfirmado == null || !usuarioConfirmado.estaHabilitado()) {
            confirmado = false;
        }

        return confirmado;
	}

	public boolean usuarioEstaEmGrupoAninhado(Grupo grupo, Usuario usuario) {
		boolean estaEmGrupoAninhado = false;

        Iterator<MembroGrupo> iter =
            grupo.membros().iterator();

        while (!estaEmGrupoAninhado && iter.hasNext()) {
            MembroGrupo membro = iter.next();
            if (membro.ehGrupo()) {
                Grupo g =
                        grupoRepositorio
                            .grupoChamado(membro.hospedeId(), membro.nome());
                if (g != null) {
                    estaEmGrupoAninhado = g.ehMembro(usuario);
                }
            }
        }

        return estaEmGrupoAninhado;
	}

}
