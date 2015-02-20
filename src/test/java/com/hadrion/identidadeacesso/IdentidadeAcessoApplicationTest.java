package com.hadrion.identidadeacesso;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.identidadeacesso.dominio.acesso.papel.Papel;
import com.hadrion.identidadeacesso.dominio.identidade.Ativacao;
import com.hadrion.identidadeacesso.dominio.identidade.ConviteRegistro;
import com.hadrion.identidadeacesso.dominio.identidade.Hospede;
import com.hadrion.identidadeacesso.dominio.identidade.HospedeId;
import com.hadrion.identidadeacesso.dominio.identidade.HospedeRepositorio;
import com.hadrion.identidadeacesso.dominio.identidade.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={IdentidadeAcessoApplication.class}, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@Transactional
public abstract class IdentidadeAcessoApplicationTest extends IdentidadeAcessoTest{
    private Hospede hospede;
    
    @Autowired
    private HospedeRepositorio hospedeRepositorio;
    
	protected Hospede hospedeFixture() {

        if (this.hospede == null) {
            HospedeId hospedeId =
                hospedeRepositorio.proximaIdentidade();

            this.hospede =
                new Hospede(
                        hospedeId,
                        FIXTURE_HOSPEDE_NOME,
                        FIXTURE_HOSPEDE_DESCRICAO,
                        true);

            hospedeRepositorio.salvar(hospede);
        }

        return this.hospede;
    }
	
	protected Usuario usuarioFixture(String username, 
			String senha){
        Hospede hospede = this.hospedeFixture();

        ConviteRegistro convite =
            this.conviteRegistro(hospede);

        Usuario usuario =
            hospede.registrarUsuario(
                    convite.conviteId(),
                    username,
                    senha,
                    new Ativacao(true, null, null),
                    this.pessoaEntidade(hospede));

        return usuario;
    }
	
	protected Usuario usuarioFixture(){
		Hospede hospede = this.hospedeFixture();
		
		ConviteRegistro convite =
				this.conviteRegistro(hospede);
		
		Usuario usuario =
				hospede.registrarUsuario(
						convite.conviteId(),
						FIXTURE_USERNAME,
						FIXTURE_SENHA,
						new Ativacao(true, null, null),
						this.pessoaEntidade(hospede));
		
		return usuario;
	}
	protected Papel papelFixture(){
		return this.hospedeFixture()
				.proverPapel(FIXTURE_PAPEL, "Um papel de teste.",true);
	}
}
