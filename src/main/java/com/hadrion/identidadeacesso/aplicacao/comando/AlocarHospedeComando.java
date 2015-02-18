package com.hadrion.identidadeacesso.aplicacao.comando;


public class AlocarHospedeComando {
	
	private String nome;
	private String descricao;
	private String nomeAdministrador;
	private String emailAdministrador;
	
	public AlocarHospedeComando() {
		super();
	}
	public AlocarHospedeComando(String nome, String descricao,
			String nomeAdministrador, String emailAdministrador) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.nomeAdministrador = nomeAdministrador;
		this.emailAdministrador = emailAdministrador;
	}
	public String getNome() {
		return nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public String getNomeAdministrador() {
		return nomeAdministrador;
	}
	public String getEmailAdministrador() {
		return emailAdministrador;
	}
	
	
}
