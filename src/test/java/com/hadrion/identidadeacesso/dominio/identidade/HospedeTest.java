package com.hadrion.identidadeacesso.dominio.identidade;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.identidadeacesso.IdentidadeAcessoApplicationTest;

public class HospedeTest extends IdentidadeAcessoApplicationTest{
	
	@Autowired
	private AlocacaoHospedeService alocacaoHospedeService;
	
	@Test
	public void alocarHospede(){
		
		Hospede hospede = alocacaoHospedeService
			.alocarHospede(
				FIXTURE_HOSPEDE_NOME,
				FIXTURE_HOSPEDE_DESCRICAO,
				"Steve Jobs",
				new Email(FIXTURE_USUARIO_EMAIL_ENDERECO));
		
		assertNotNull(hospede.hospedeId());
		assertNotNull(hospede.hospedeId().id());
		assertEquals(36, hospede.hospedeId().id().length());
		assertEquals(FIXTURE_HOSPEDE_NOME, hospede.nome());
		assertEquals(FIXTURE_HOSPEDE_DESCRICAO, hospede.descricao());
	}
	
	@Test
	public void criarConvidePerpetuo() throws Exception {

        Hospede hospede = this.hospedeFixture();

        hospede
            .oferecerConviteRegistro("Perpétuo")
            .perpetuo();

        assertNotNull(hospede.redefinirConviteRegistroComo("Perpétuo"));
    }
	
	@Test
	public void convidePerpetuoDisponivel() throws Exception {

        Hospede hospede = this.hospedeFixture();

        hospede
            .oferecerConviteRegistro("Perpétuo")
            .perpetuo();

        assertTrue(hospede.registroEstaDisponivelPara("Perpétuo"));
    }
	
	@Test
	public void convideDuracaoDeterminadaDisponivel() throws Exception {

        Hospede hospede = this.hospedeFixture();

        hospede
            .oferecerConviteRegistro("Hoje-e-amanhã")
            .comecandoEm(this.hoje())
            .ate(this.amanha());

        assertTrue(hospede.registroEstaDisponivelPara("Hoje-e-amanhã"));
    }
	
	@Test
	public void convideDuracaoDeterminadaNaoDisponivel() throws Exception {
		
		Hospede hospede = this.hospedeFixture();
		
		hospede
		.oferecerConviteRegistro("Amanhã-e-Depois-de-Amanhã")
		.comecandoEm(this.amanha())
		.ate(this.depoisDeAmanha());
		
		assertFalse(hospede.registroEstaDisponivelPara("Amanhã-e-Depois-de-Amanhã"));
	}
	
	@Test
	public void descritorConviteDisponivel(){

		Hospede hospede = this.hospedeFixture();

		hospede.oferecerConviteRegistro("Perpétuo").perpetuo();

		hospede.oferecerConviteRegistro("Hoje-e-Amanhã")
				.comecandoEm(this.hoje()).ate(this.amanha());

		assertEquals(hospede.todosConvitesRegistroDisponiveis().size(), 2);
	}
	
	@Test
	public void descritorConviteNaoDisponivel(){
		
		Hospede hospede = this.hospedeFixture();
		
		hospede.oferecerConviteRegistro("Perpétuo").perpetuo();
		
		hospede.oferecerConviteRegistro("Amanhã-e-Depois-de-Amanhã")
		.comecandoEm(this.amanha()).ate(this.depoisDeAmanha());
		
		assertEquals(hospede.todosConvitesRegistroDisponiveis().size(), 1);
	}
	
	@Test
	public void desativarHospede(){
		
		Hospede hospede = hospedeFixture();
		
		assertTrue(hospede.estaAtivo());
		
		hospede.desativar();
		
		assertFalse(hospede.estaAtivo());
	}
	
	@Test
	public void registrarUsuario(){
		
		Hospede hospede = hospedeFixture();
		
		ConviteRegistro convite = this.conviteRegistro(hospede);
		
		Usuario usuario = 
			hospede.registrarUsuario(
					convite.conviteId(),
					FIXTURE_USERNAME,
					FIXTURE_SENHA,
					new Ativacao(true, null, null),
					this.pessoaEntidade(hospede));
		
		assertNotNull(usuario.ativacao());
		assertNotNull(usuario.pessoa());
		assertNotNull(usuario.paraDescritorUsuario());
	}
}
