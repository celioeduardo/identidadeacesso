package com.hadrion.identidadeacesso.dominio.identidade;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.hadrion.identidadeacesso.IdentidadeAcessoApplicationTest;
import com.hadrion.identidadeacesso.dominio.identidade.grupo.Grupo;
import com.hadrion.identidadeacesso.dominio.identidade.grupo.GrupoRepositorio;

public class GrupoTest extends IdentidadeAcessoApplicationTest{

	@Autowired
	private GrupoRepositorio grupoRepositorio;
	
	@Test
	public void proverGrupo(){
		Hospede hospede = this.hospedeFixture();
		Grupo grupoA = hospede.proverGrupo("Grupo A", "Um grupo chamado Grupo A");
		grupoRepositorio.adicionar(grupoA);
		assertEquals(1, grupoRepositorio.todosGrupos(hospede.hospedeId()).size());
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void grupoUnico(){
		Hospede hospede = this.hospedeFixture();
		Grupo grupoA = hospede.proverGrupo("Grupo A", "Um grupo chamado Grupo A");
		grupoRepositorio.adicionar(grupoA);
		
		hospede.proverGrupo("Grupo A", "Um grupo chamado Grupo A");
		grupoRepositorio.adicionar(grupoA);
		
	}
	
	@Test
	public void adicionarGrupo(){
		Hospede hospede = this.hospedeFixture();
		Grupo grupoA = hospede.proverGrupo("Grupo A", "Um grupo chamado Grupo A");
		grupoRepositorio.adicionar(grupoA);
		
		Grupo grupoB = hospede.proverGrupo("Grupo B", "Um grupo chamado Grupo B");
		grupoRepositorio.adicionar(grupoB);
		
		grupoA.adicionarGrupo(grupoB);
		
		assertEquals(1,grupoA.quantidadeMembros());
		assertEquals(0,grupoB.quantidadeMembros());
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void recursaoNoGrupo(){
		Hospede hospede = this.hospedeFixture();
		Grupo grupoA = hospede.proverGrupo("Grupo A", "Um grupo chamado Grupo A");
		grupoRepositorio.adicionar(grupoA);
		
		Grupo grupoB = hospede.proverGrupo("Grupo B", "Um grupo chamado Grupo B");
		grupoRepositorio.adicionar(grupoB);
		
		grupoA.adicionarGrupo(grupoB);
		grupoB.adicionarGrupo(grupoA);
	}
	
}
