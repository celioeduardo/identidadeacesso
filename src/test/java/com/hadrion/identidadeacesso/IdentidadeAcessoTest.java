package com.hadrion.identidadeacesso;

import java.util.Date;

import org.springframework.test.context.ActiveProfiles;

import com.hadrion.identidadeacesso.dominio.identidade.ConviteRegistro;
import com.hadrion.identidadeacesso.dominio.identidade.Email;
import com.hadrion.identidadeacesso.dominio.identidade.Hospede;
import com.hadrion.identidadeacesso.dominio.identidade.InformacaoContato;
import com.hadrion.identidadeacesso.dominio.identidade.Pessoa;

@ActiveProfiles("test")
public abstract class IdentidadeAcessoTest {

	protected static final String FIXTURE_SENHA = "SenhaSecreta!";
	protected static final String FIXTURE_HOSPEDE_DESCRICAO = "Este é um hóspede de teste.";
	protected static final String FIXTURE_HOSPEDE_NOME = "Hóspede Teste";
	protected static final String FIXTURE_USUARIO_EMAIL_ENDERECO = "teste@hadrion.com.br";
	protected static final String FIXTURE_USERNAME = "testehdr";
	protected static final long VINTE_E_QUATRO_HORAS = (1000L * 60L * 60L * 24L);

	protected static final String FIXTURE_USERNAME2 = "testehdr2";
	protected static final String FIXTURE_USUARIO_EMAIL_ENDERECO2 = "teste2@hadrion.com.br";
	
	protected Date anteontem() {
		return new Date(this.hoje().getTime() - (VINTE_E_QUATRO_HORAS * 2));
	}

	protected Date depoisDeAmanha() {
		return new Date(this.hoje().getTime() + (VINTE_E_QUATRO_HORAS * 2));
	}

	protected Date ontem() {
	    return new Date(this.hoje().getTime() - VINTE_E_QUATRO_HORAS);
	}

	protected Date amanha() {
		return new Date(this.hoje().getTime() + VINTE_E_QUATRO_HORAS);
	}

	protected Date hoje() {
		return new Date();
	}

	protected ConviteRegistro conviteRegistro(Hospede hospede) {

        Date hoje = new Date();

        Date amanha = new Date(hoje.getTime() + VINTE_E_QUATRO_HORAS);

        ConviteRegistro registroConvite =
            hospede.oferecerConviteRegistro("Hoje-e-Amanhã: " + System.nanoTime())
            .comecandoEm(hoje)
            .ate(amanha);

        return registroConvite;
    }

	protected Pessoa pessoaEntidade(Hospede hospede) {

		Pessoa person = new Pessoa(
				hospede.hospedeId(), 
				"Steve Jobs", 
				this.informacaoContato());

		return person;
	}
	
	
	protected InformacaoContato informacaoContato() {
        return
            new InformacaoContato(
                    new Email(FIXTURE_USUARIO_EMAIL_ENDERECO));
    }
}
