package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = -5616324017094646008L;


	public PedidoNaoEncontradoException(String codigo) {
		super(String.format("Pedido com codigo:%s não encontrado.", codigo));
	}

}
