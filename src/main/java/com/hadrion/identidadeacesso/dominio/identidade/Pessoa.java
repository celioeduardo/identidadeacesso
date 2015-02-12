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
@SequenceGenerator(name="SEQ", sequenceName="SQ_PESSOA")
@Table(name="PESSOA")
public class Pessoa extends Afirmacao{
	
	@Embedded
	private HospedeId hospedeId;
	
	@Column(name="NOME")
	private String nome;
	
	@Embedded
	private InformacaoContato informacaoContato;
	
	@OneToOne(cascade=CascadeType.ALL)
    private Usuario usuario;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;
	
    public Pessoa(HospedeId hospedeId, String nome,
			InformacaoContato informacaoContato) {
		super();
		this.hospedeId = hospedeId;
		this.nome = nome;
		this.setInformacaoContato(informacaoContato);
	}
    
    @SuppressWarnings("unused")
	private Pessoa() {}
    
	public HospedeId hospedeId(){
    	return hospedeId;
    }
    
    public String nome(){
    	return nome;
    }
    
    public InformacaoContato informacaoContato(){
    	return informacaoContato;
    }
    
    public Usuario usuario(){
    	return usuario;
    }
    
    void somenteInternoSetUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    @Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Pessoa objetoTipado = (Pessoa) objeto;
			objetosIguais = new EqualsBuilder()
				.append(hospedeId(),objetoTipado.hospedeId())
				.append(nome(),objetoTipado.nome())
				.append(informacaoContato(),objetoTipado.informacaoContato())
				.append(usuario(),objetoTipado.usuario())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(341,467)
			.append(hospedeId())
			.append(nome())
			.append(informacaoContato())
			.append(usuario())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Pessoa [hospedeId=" + hospedeId()
				+ "nome=" + nome()
				+ "informacaoContato=" + informacaoContato()
				+ "usuario=" + usuario()
				+ "]";
	}

	protected void setHospedeId(HospedeId hospedeId) {
		this.assertArgumentoNaoNulo(hospedeId, "O hospedeId é obrigatório.");
		this.hospedeId = hospedeId;
	}

	public Email email() {
		return informacaoContato().email();
	}

	public void alterarInformacaoContato(InformacaoContato informacaoContato) {
		this.setInformacaoContato(informacaoContato);
		
	}
	
	private void setInformacaoContato(InformacaoContato informacaoContato){
		this.assertArgumentoNaoNulo(informacaoContato, "A informação de contato da pessoa é obrigatória.");
		this.informacaoContato = informacaoContato;
	}

	public void alterarNome(String novoNome) {
		this.setNome(novoNome);
	}

	private void setNome(String nome) {
		this.assertArgumentoNaoVazio(nome, "O nome da pessoa é obrigatório.");
		this.nome = nome;
	}
    
}
