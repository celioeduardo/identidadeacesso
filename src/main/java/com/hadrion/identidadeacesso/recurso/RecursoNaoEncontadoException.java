package com.hadrion.identidadeacesso.recurso;


public class RecursoNaoEncontadoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public RecursoNaoEncontadoException(String mensagem){
		super(mensagem);
	}
	
}
