package com.hadrion.identidadeacesso.dominio.identidade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hadrion.identidadeacesso.IdentidadeAcessoApplicationTest;

public class UsuarioTest extends IdentidadeAcessoApplicationTest{
	
	@Test
	public void ativacaoUsuarioHabilidata(){
		
		Usuario usuario = this.usuarioFixture();
		
		assertTrue(usuario.estaHabilitado());
	}
	
	@Test
	public void ativacaoUsuarioDesabilidata(){
		
		Usuario usuario = this.usuarioFixture();
		
		usuario.definirAtivacao(new Ativacao(false,null,null));
		
		assertFalse(usuario.estaHabilitado());
	}
	
	@Test
	public void ativacaoUsuarioDentroDasDatasInicioFim(){
		
		Usuario usuario = this.usuarioFixture();
		
		usuario.definirAtivacao(
				new Ativacao(
						true,
						this.hoje(),
						this.amanha()));
		
		assertTrue(usuario.estaHabilitado());
	}
	
	@Test
	public void ativacaoUsuarioForaDasDatasInicioFim(){
		
		Usuario usuario = this.usuarioFixture();
		
		usuario.definirAtivacao(
				new Ativacao(
						true,
						this.anteontem(),
						this.ontem()));
		
		assertFalse(usuario.estaHabilitado());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void ativacaoUsuarioDatasNaoSequenciais(){
		
		Usuario usuario = this.usuarioFixture();
		
		usuario.definirAtivacao(
				new Ativacao(
						true,
						this.amanha(),
						this.hoje()));
		
	}
	
	@Test
	public void descritorUsuario(){
		
		Usuario usuario = this.usuarioFixture();
		
		DescritorUsuario descritor = usuario.paraDescritorUsuario();
		
		assertNotNull(descritor.email());
        assertEquals(descritor.email(), FIXTURE_USUARIO_EMAIL_ENDERECO);

        assertNotNull(descritor.hospedeId());
        assertEquals(descritor.hospedeId(), usuario.hospedeId());

        assertNotNull(descritor.username());
        assertEquals(descritor.username(), FIXTURE_USERNAME);
	}
	
	@Test
	public void alterarSenha(){
		
		Usuario usuario = this.usuarioFixture();
		
		usuario.alterarSenha(FIXTURE_SENHA,"EstaÉumaNovaSenha");
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void falhaAlterarSenha(){
		
		Usuario usuario = this.usuarioFixture();
		
		usuario.alterarSenha("não sei","EstaÉumaNovaSenha");
	}

	@Test
	public void senhaHasheadaNaContrucao(){
		
		Usuario usuario = this.usuarioFixture();
		
		assertNotEquals(FIXTURE_SENHA, usuario.senha());
	}
	
	@Test
	public void senhaHasheadaNaAlteracao(){
		
		Usuario usuario = this.usuarioFixture();
		
		String senhaForte = DominioRegistro.senhaService()
				.gerarSenhaForte();		
		
		usuario.alterarSenha(FIXTURE_SENHA,senhaForte);
		
		assertNotEquals(FIXTURE_SENHA, usuario.senha());
		assertNotEquals(senhaForte, usuario.senha());
	}
	
	@Test
	public void alterarInformacaoContato(){
		
		Usuario usuario = this.usuarioFixture();
		
		usuario.alterarInformacaoContato(
				new InformacaoContato(
						new Email(FIXTURE_USUARIO_EMAIL_ENDERECO2)));
		
		assertEquals(new Email(FIXTURE_USUARIO_EMAIL_ENDERECO2),usuario.pessoa().email());
	}
	
	@Test
	public void alterarNomePessoa(){
		
		Usuario usuario = this.usuarioFixture();
		
		usuario.alterarNomePessoal("Martin Fowler");
		
		assertEquals("Martin Fowler",usuario.pessoa().nome());
	}
	
}
