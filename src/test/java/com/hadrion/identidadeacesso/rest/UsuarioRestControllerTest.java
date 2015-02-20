package com.hadrion.identidadeacesso.rest;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.identidadeacesso.aplicacao.IdentidadeAcessoAplicacaoService;
import com.hadrion.identidadeacesso.dominio.identidade.Usuario;
import com.hadrion.identidadeacesso.dominio.identidade.UsuarioRepositorio;
public class UsuarioRestControllerTest extends AbstractRestControllerTest {
	
	@Autowired
	private IdentidadeAcessoAplicacaoService servico;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Before
	public void setup() {
		super.setup();
	}
	
	@Test
    public void autenticacaoOk() throws Exception {
		String senha = FIXTURE_SENHA;
		
		Usuario usuario = this.usuarioFixture(FIXTURE_USERNAME, senha);
		
		usuarioRepositorio.salvar(usuario);
		
		String url = format("/hospedes/%s/usuarios/%s/autenticadoPor/%s",
				usuario.hospedeId(),
				usuario.username(),
				senha);
			
        mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.usuario.hospedeId", is(usuario.hospedeId().id())))
            .andExpect(jsonPath("$.usuario.username", is(usuario.username())))
            .andExpect(jsonPath("$.usuario.email", is(usuario.pessoa().email().endereco())));
    }
	
	@Test
    public void autenticacaoFalhaHospede() throws Exception {
		String senha = FIXTURE_SENHA;
		
		Usuario usuario = this.usuarioFixture(FIXTURE_USERNAME, senha);
		
		usuarioRepositorio.salvar(usuario);
		
		String url = format("/hospedes/%s/usuarios/%s/autenticadoPor/%s",
				UUID.randomUUID().toString(),
				usuario.username(),
				senha);

		mockMvc.perform(get(url))
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(contentType));
    }
	
	@Test
    public void autenticacaoUsernameInexistente() throws Exception {
		String senha = FIXTURE_SENHA;
		
		Usuario usuario = this.usuarioFixture(FIXTURE_USERNAME, senha);
		
		usuarioRepositorio.salvar(usuario);
		
		String url = format("/hospedes/%s/usuarios/%s/autenticadoPor/%s",
				usuario.hospedeId(),
				"usuarioqualquer",
				senha);

		mockMvc.perform(get(url))
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(contentType));
    }
	
	@Test
	public void autenticacaoSenhaErrada() throws Exception {
		String senha = FIXTURE_SENHA;
		
		Usuario usuario = this.usuarioFixture(FIXTURE_USERNAME, senha);
		
		usuarioRepositorio.salvar(usuario);
		
		String url = format("/hospedes/%s/usuarios/%s/autenticadoPor/%s",
				usuario.hospedeId(),
				usuario.username(),
				"não tenho idéia");
		
		mockMvc.perform(get(url))
		.andExpect(status().is4xxClientError())
		.andExpect(content().contentType(contentType));
	}
}
