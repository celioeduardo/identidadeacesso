package com.hadrion.identidadeacesso.dominio.identidade;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hadrion.identidadeacesso.IdentidadeAcessoTest;


public class AtivacaoTest extends IdentidadeAcessoTest{
	
	@Test
	public void ativacaoHabilidata(){
		Ativacao ativacao = new Ativacao(true, null, null);
		assertTrue(ativacao.estaHabilitada());
	}
	
	@Test
	public void ativacaoDesabilidata(){
		Ativacao ativacao = new Ativacao(false, null, null);
		assertFalse(ativacao.estaHabilitada());
	}
	
	@Test
	public void foraDasDatasInicioFim(){
		
		Ativacao ativacao = 
			new Ativacao(
				true,
				this.anteontem(),
				this.ontem());
		
		assertFalse(ativacao.estaHabilitada());
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void datasNaoSequenciais(){
		new Ativacao(
			true,
			this.amanha(),
			this.hoje());
	}
	
	@Test
	public void tempoFimExpirado(){
		Ativacao ativacao = 
				new Ativacao(
					true,
					this.anteontem(),
					this.ontem());
		
		assertTrue(ativacao.estaTempoExpirado());
	}
	
	@Test
	public void naoIniciouPeriodoAtivacao(){
		Ativacao ativacao = 
				new Ativacao(
					true,
					this.amanha(),
					this.depoisDeAmanha());
		
		assertTrue(ativacao.estaTempoExpirado());
	}
	
}
