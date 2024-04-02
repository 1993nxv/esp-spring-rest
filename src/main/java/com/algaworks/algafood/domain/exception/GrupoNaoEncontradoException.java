package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = -5616324017094646008L;

	public GrupoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public GrupoNaoEncontradoException(Long id) {
		this(String.format("Grupo com id:%d não encontrado.", id));
	}

}
