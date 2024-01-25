package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = -1368703488857597431L;

	public CozinhaNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public CozinhaNaoEncontradoException(Long id) {
		this(String.format("Cozinha com id:%d não encontrada.", id));
	}

}
