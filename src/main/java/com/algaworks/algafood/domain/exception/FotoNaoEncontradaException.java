package com.algaworks.algafood.domain.exception;

public class FotoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 3578989117900232130L;

	public FotoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public FotoNaoEncontradaException(Long id) {
		this(String.format("Foto para o produto com id:%d não encontrada.", id));
	}

}
