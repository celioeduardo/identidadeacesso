package com.hadrion.identidadeacesso.dominio.identidade;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.identidadeacesso.comum.Afirmacao;

@Entity
@SequenceGenerator(name="SEQ", sequenceName="SQ_USUARIO")
@Table(name="USUARIO")
public class Usuario extends Afirmacao{
	
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="SENHA")
	private String senha;
	
	@Embedded
	private Ativacao ativacao;
	
	@OneToOne(cascade=CascadeType.ALL)
    private Pessoa pessoa;
	
	@Embedded
	private HospedeId hospedeId;
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;
	
	public Usuario(HospedeId hospedeId, String username, String senha,
			Ativacao ativacao, Pessoa pessoa) {
		
		this.hospedeId = hospedeId;
		this.username = username;
		this.ativacao = ativacao;
		this.pessoa = pessoa;
		
		this.protegerSenha("", senha);
		this.senha = comoValorEncriptado(senha);
		
		this.pessoa.somenteInternoSetUsuario(this);
		
	}
	
	@SuppressWarnings("unused")
	private Usuario(){}
	
	public Ativacao ativacao() {
		return ativacao;
	}

	public Pessoa pessoa() {
		return pessoa;
	}

	public DescritorUsuario paraDescritorUsuario() {
		return new DescritorUsuario(
				hospedeId(), 
				username(), 
				pessoa().email().endereco());
	}
	
	public HospedeId hospedeId(){
		return hospedeId;
	}
	
	public String username(){
		return username;
	}
	
	public String somenteAcessoInternoSenhaEncriptada() {
        return this.senha();
    }

    protected String senha() {
        return this.senha;
    }
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Usuario objetoTipado = (Usuario) objeto;
			objetosIguais = new EqualsBuilder()
				.append(hospedeId(),objetoTipado.hospedeId())
				.append(username(),objetoTipado.username())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(521,97)
			.append(hospedeId())
			.append(username())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Usuario [hospedeId=" + hospedeId()
				+ "username=" + username()
				+ "ativacao=" + ativacao()
				+ "pessoa=" + pessoa()
				+ "]";
	}

	public boolean estaHabilitado() {
		return ativacao().estaHabilitada();
	}

	public void definirAtivacao(Ativacao ativacao) {
		this.ativacao = ativacao;
	}

	public void alterarSenha(String senhaAtual, String novaSenha) {
		this.assertArgumentoNaoVazio(
				senhaAtual, 
				"As senhas nova e atual devem ser informadas.");
		
		this.assertArgumentoEquals(
				senha(), 
				comoValorEncriptado(senhaAtual),
				"Senha atual não confere.");
		
		this.protegerSenha(senhaAtual, novaSenha);
		
		this.setSenha(comoValorEncriptado(novaSenha));
		
	}
	
	protected void protegerSenha(String senhaAtual, String novaSenha) {
        this.assertSenhasNaoSaoAsMesmas(senhaAtual, novaSenha);

        this.assertSenhaNaoFraca(novaSenha);

        this.assertUsuarioSenhaNaoSaoMesmos(novaSenha);

        this.setSenha(this.comoValorEncriptado(novaSenha));
    }
	
	private void assertUsuarioSenhaNaoSaoMesmos(String novaSenha) {
		this.assertArgumentoNotEquals(
				username(), 
				novaSenha, 
				"O usuário e a senha não devem ser os mesmos.");
	}

	private void assertSenhaNaoFraca(String novaSenha) {
		this.assertArgumentoFalso(
				DominioRegistro.senhaService().ehFraca(novaSenha), 
				"A senha precisa ser mais forte.");
		
	}

	private void assertSenhasNaoSaoAsMesmas(String senhaAtual, String novaSenha) {
		 this.assertArgumentoNotEquals(
				 senhaAtual, 
				 novaSenha, 
				 "A senha está inalterada");
	}

	private void setSenha(String senha) {
		this.senha = senha;
	}

	protected String comoValorEncriptado(String valor){
		return DominioRegistro
				.encriptacaoService()
				.valorEncriptado(valor);
	}

	public void alterarInformacaoContato(InformacaoContato informacaoContato) {
		pessoa().alterarInformacaoContato(informacaoContato);
	}

	public void alterarNomePessoal(String novoNome) {
		this.pessoa().alterarNome(novoNome);
		
	}

}
