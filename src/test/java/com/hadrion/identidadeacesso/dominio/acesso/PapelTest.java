package com.hadrion.identidadeacesso.dominio.acesso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.hadrion.identidadeacesso.IdentidadeAcessoApplicationTest;
import com.hadrion.identidadeacesso.dominio.acesso.papel.Papel;
import com.hadrion.identidadeacesso.dominio.acesso.papel.PapelRepositorio;
import com.hadrion.identidadeacesso.dominio.identidade.Hospede;
import com.hadrion.identidadeacesso.dominio.identidade.Usuario;
import com.hadrion.identidadeacesso.dominio.identidade.UsuarioRepositorio;
import com.hadrion.identidadeacesso.dominio.identidade.grupo.Grupo;
import com.hadrion.identidadeacesso.dominio.identidade.grupo.GrupoRepositorio;

public class PapelTest extends IdentidadeAcessoApplicationTest{
	
	@Autowired
	private PapelRepositorio papelRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private GrupoRepositorio grupoRepositorio;
	
	@Test
	public void proverPapel(){
		Hospede hospede = this.hospedeFixture();
		Papel papel = hospede.proverPapel("Gerente","Um papel de gerente.");
		papelRepositorio.adicionar(papel);
		assertEquals(1,papelRepositorio.todosPapeis().size());
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void roleUnica(){
		Hospede hospede = this.hospedeFixture();
		Papel papel = hospede.proverPapel("Gerente","Um papel de gerente.");
		papelRepositorio.adicionar(papel);
		
		papel = hospede.proverPapel("Gerente","Um papel de gerente.");
		papelRepositorio.adicionar(papel);
		
	}
	
	@Test
	public void usuarioEstaNoPapel(){
		Hospede hospede = this.hospedeFixture();
		Usuario usuario = this.usuarioFixture();
		usuarioRepositorio.salvar(usuario);
		
		Papel papelGerente = hospede.proverPapel("Gerente", "Um papel de gerente");
		papelGerente.associarUsuario(usuario);
		papelRepositorio.adicionar(papelGerente);
		
		assertTrue(papelGerente.estaNoPapel(usuario));
		
	}
	
	@Test
	public void usuarioEstaNoGrupoComPapel(){
		Hospede hospede = this.hospedeFixture();
		
		Usuario usuario = this.usuarioFixture();
		usuarioRepositorio.salvar(usuario);
		
		Grupo gerentes = hospede.proverGrupo("Gerentes", "Um grupo de gerentes");
		grupoRepositorio.adicionar(gerentes);
		
		Papel papelGerente = hospede.proverPapel("Gerente", "Um papel de gerente",true);
		papelGerente.associarGrupo(gerentes);
		papelRepositorio.adicionar(papelGerente);
		
		gerentes.adicionarUsuario(usuario);
		
		assertTrue(papelGerente.estaNoPapel(usuario));
		assertTrue(gerentes.ehMembro(usuario));
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void papelQueNaoAceitaAninhamento(){
		Hospede hospede = this.hospedeFixture();
		
		Usuario usuario = this.usuarioFixture();
		usuarioRepositorio.salvar(usuario);
		
		Grupo gerentes = hospede.proverGrupo("Gerentes", "Um grupo de gerentes");
		grupoRepositorio.adicionar(gerentes);
		
		Papel papelGerente = hospede.proverPapel("Gerente", "Um papel de gerente");
		papelGerente.associarGrupo(gerentes);
		papelRepositorio.adicionar(papelGerente);
		
		gerentes.adicionarUsuario(usuario);
		
		assertTrue(papelGerente.estaNoPapel(usuario));
		assertTrue(gerentes.ehMembro(usuario));
		
	}
	
	
}
