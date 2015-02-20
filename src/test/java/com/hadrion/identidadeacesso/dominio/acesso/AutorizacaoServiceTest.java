package com.hadrion.identidadeacesso.dominio.acesso;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.identidadeacesso.IdentidadeAcessoApplicationTest;
import com.hadrion.identidadeacesso.dominio.acesso.papel.Papel;
import com.hadrion.identidadeacesso.dominio.acesso.papel.PapelRepositorio;
import com.hadrion.identidadeacesso.dominio.identidade.Hospede;
import com.hadrion.identidadeacesso.dominio.identidade.Usuario;
import com.hadrion.identidadeacesso.dominio.identidade.UsuarioRepositorio;

public class AutorizacaoServiceTest extends IdentidadeAcessoApplicationTest {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private PapelRepositorio papelRepositorio;
	
	@Autowired
	private AutorizacaoService autorizacaoService;
	
	@Test
	public void usuarioNoPapel(){
		Hospede hospede = this.hospedeFixture();
		Usuario usuario = this.usuarioFixture();
		usuarioRepositorio.salvar(usuario);
		
		Papel papelGerente = hospede.proverPapel("Gerente","Um papel de gerente.",true);
		
		papelGerente.associarUsuario(usuario);
		
		papelRepositorio.adicionar(papelGerente);
		
		assertTrue(
			autorizacaoService
				.usuarioEstaNoPapel(usuario,"Gerente"));
		
		assertFalse(
				autorizacaoService
				.usuarioEstaNoPapel(usuario,"Diretor"));
	}
	
	@Test
	public void usernameNoPapel(){
		Hospede hospede = this.hospedeFixture();
		Usuario usuario = this.usuarioFixture();
		usuarioRepositorio.salvar(usuario);
		
		Papel papelGerente = hospede.proverPapel("Gerente","Um papel de gerente.",true);
		
		papelGerente.associarUsuario(usuario);
		
		papelRepositorio.adicionar(papelGerente);
		
		assertTrue(
				autorizacaoService
				.usuarioEstaNoPapel(hospede.hospedeId(),usuario.username(),"Gerente"));
		
		assertFalse(
				autorizacaoService
				.usuarioEstaNoPapel(hospede.hospedeId(),usuario.username(),"Diretor"));
	}
}
