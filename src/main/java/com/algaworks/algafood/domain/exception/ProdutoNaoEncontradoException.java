package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = -1765438998990620329L;

	public ProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public ProdutoNaoEncontradoException(Long id) {
		this(String.format("Produto com id:%d não encontrado.", id));
	}

}
