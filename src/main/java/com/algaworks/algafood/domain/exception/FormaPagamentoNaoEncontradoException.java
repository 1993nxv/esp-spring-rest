package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = -4854690016530138493L;

	public FormaPagamentoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public FormaPagamentoNaoEncontradoException(Long id) {
		this(String.format("Forma de pagamento com id:%d não encontrada.", id));
	}

}
