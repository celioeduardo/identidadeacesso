package com.hadrion.identidadeacesso.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.hadrion.identidadeacesso.aplicacao.data.DescritorUsuarioData;

@Component
public class UsuarioResourceAssembler extends ResourceAssemblerSupport<DescritorUsuarioData, UsuarioRecurso> {

	public UsuarioResourceAssembler() {
		super(UsuarioRestController.class, UsuarioRecurso.class);
	}

	@Override
	public UsuarioRecurso toResource(DescritorUsuarioData usuario) {
		UsuarioRecurso recurso = createResourceWithId(usuario.getUsername(),usuario,
				usuario.getHospedeId(),
				usuario.getUsername());
		recurso.add(linkTo(UsuarioRestController.class,usuario.getHospedeId())
				.withRel("usuarios"));
		return recurso;
	}
	
	@Override
    protected UsuarioRecurso instantiateResource(DescritorUsuarioData usuario) {
        return new UsuarioRecurso(usuario);
    }

}
