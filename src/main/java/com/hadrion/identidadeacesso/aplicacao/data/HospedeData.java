package com.hadrion.identidadeacesso.aplicacao.data;


public class HospedeData {
	private String hospedeId;
	private String nome;
	private String descricao;
	private boolean ativo;
	
	public HospedeData() {
		super();
	}

	public String getHospedeId() {
		return hospedeId;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public HospedeData(String hospedeId, String nome, String descricao,
			boolean ativo) {
		super();
		this.hospedeId = hospedeId;
		this.nome = nome;
		this.descricao = descricao;
		this.ativo = ativo;
	}
	
	
}
