package com.hadrion.identidadeacesso.infraestrutura.agrix;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.hadrion.identidadeacesso.IdentidadeAcessoApplicationTest;
import com.hadrion.identidadeacesso.dominio.identidade.AutenticacaoService;
import com.hadrion.identidadeacesso.dominio.identidade.DescritorUsuario;
import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;

@ActiveProfiles({"test","agrix"})
public class AutenticacaoAgrixServiceTest extends IdentidadeAcessoApplicationTest {

	@Autowired
	private AgrixConfiguracao configuracao;
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@InjectMocks
	private AutenticacaoAgrixService autenticacaoMockService;
	
	@Mock
	private AgrixConfiguracao agrixConfiguracao;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test @Ignore
	public void configuracao(){
		assertEquals("jdbc:oracle:thin:@192.168.151.3:1521:orcl", configuracao.getUrl());
	}
	
	@Test @Ignore
	public void autenticacaoOk(){
		HospedeId hospedeId = new HospedeId(UUID.randomUUID().toString().toLowerCase());
		DescritorUsuario usuario = autenticacaoService.autenticar(hospedeId, "hadrion", "sucesso");
		
		assertEquals(new DescritorUsuario(hospedeId, "hadrion", null),usuario);
	}
	
	@Test @Ignore
	public void autenticacaoUsuarioIncorreto(){
		HospedeId hospedeId = new HospedeId(UUID.randomUUID().toString().toLowerCase());
		DescritorUsuario usuario = autenticacaoService.autenticar(hospedeId, "abcdef", "sucesso");
		
		assertEquals(DescritorUsuario.intanciaDescritorNula(),usuario);
	}
	
	@Test @Ignore
	public void autenticacaoSenhaIncorreta(){
		HospedeId hospedeId = new HospedeId(UUID.randomUUID().toString().toLowerCase());
		DescritorUsuario usuario = autenticacaoService.autenticar(hospedeId, "hadrion", "sei lá");
		
		assertEquals(DescritorUsuario.intanciaDescritorNula(),usuario);
	}
	
	@Test @Ignore
	public void autenticacaoUrlIncorreta(){
		
		Mockito.when(agrixConfiguracao.getUrl()).thenReturn("jdbc:oracle:thin:@localhost:1111:orcl");
		
		HospedeId hospedeId = new HospedeId(UUID.randomUUID().toString().toLowerCase());
		DescritorUsuario usuario = autenticacaoMockService.autenticar(hospedeId, "hadrion", "sucesso");
		
		assertEquals(DescritorUsuario.intanciaDescritorNula(),usuario);
	}
	
	
}
