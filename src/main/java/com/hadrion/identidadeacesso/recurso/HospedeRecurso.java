package com.hadrion.identidadeacesso.recurso;

import org.springframework.hateoas.ResourceSupport;

public class HospedeRecurso extends ResourceSupport {
	
	protected String hospedeId;
	protected String nome;
	protected String descricao;
	protected boolean ativo;
	
	public HospedeRecurso(String hospedeId, String nome, String descricao,
			boolean ativo) {
		super();
		this.hospedeId = hospedeId;
		this.nome = nome;
		this.descricao = descricao;
		this.ativo = ativo;
	}

	public HospedeRecurso() {
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
	
	
	
	
//	private final HospedeData hospede;
//	
//	public HospedeRecurso(HospedeData hospede){
//		this.hospede = hospede;
//		this.add(linkTo(HospedeRestController.class).withRel("hospedes"));
//		this.add(linkTo(methodOn(HospedeRestController.class)
//				.obter(hospede.getHospedeId())).withSelfRel());
//	}
//	
//	public HospedeData getHospedeData(){
//		return hospede;
//	}
	
}
