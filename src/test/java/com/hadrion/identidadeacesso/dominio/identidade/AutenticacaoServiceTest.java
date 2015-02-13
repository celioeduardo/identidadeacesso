package com.hadrion.identidadeacesso.dominio.identidade;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.identidadeacesso.IdentidadeAcessoApplicationTest;

public class AutenticacaoServiceTest extends IdentidadeAcessoApplicationTest {
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private HospedeRepositorio hospedeRepositorio;
   
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Test
    public void sucessoAutenticacao() throws Exception {

        Usuario usuario = this.usuarioFixture();
        
        usuarioRepositorio.salvar(usuario);
      
        DescritorUsuario descritorUsuario =
            autenticacaoService.autenticar(
                        usuario.hospedeId(),
                        usuario.username(),
                        FIXTURE_SENHA);

        assertNotNull(descritorUsuario);
        assertFalse(descritorUsuario.ehDescritorNulo());
        assertEquals(descritorUsuario.hospedeId(), usuario.hospedeId());
        assertEquals(descritorUsuario.username(), usuario.username());
        assertEquals(descritorUsuario.email(), usuario.pessoa().email().endereco());
    }
	
	@Test
    public void falhaAutenticacaoHospedeDiferente() throws Exception {

        Usuario usuario = this.usuarioFixture();
        
        usuarioRepositorio.salvar(usuario);

        DescritorUsuario descritorUsuario =
                autenticacaoService
                	.autenticar(
                            hospedeRepositorio.proximaIdentidade(),
                            usuario.username(),
                            FIXTURE_SENHA);
        
        assertNotNull(descritorUsuario);
        assertTrue(descritorUsuario.ehDescritorNulo());
    }

	@Test
    public void falhaAutenticacaoUsernameDiferente() throws Exception {

    	Usuario usuario = this.usuarioFixture();
        
        usuarioRepositorio.salvar(usuario);

        DescritorUsuario descritorUsuario =
                autenticacaoService
                	.autenticar(
                            usuario.hospedeId(),
                            FIXTURE_USERNAME2,
                            usuario.senha());
        
        assertNotNull(descritorUsuario);
        assertTrue(descritorUsuario.ehDescritorNulo());
    }
    
    @Test
    public void falhaAutenticacaoSenhaDiferente() throws Exception {

    	Usuario usuario = this.usuarioFixture();
        
        usuarioRepositorio.salvar(usuario);

        DescritorUsuario descritorUsuario =
                autenticacaoService
                	.autenticar(
                            usuario.hospedeId(),
                            usuario.username(),
                            FIXTURE_SENHA+"-");
        
        assertNotNull(descritorUsuario);
        assertTrue(descritorUsuario.ehDescritorNulo());
    }

}
