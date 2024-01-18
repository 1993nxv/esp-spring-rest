package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradoException extends NegocioException {

	private static final long serialVersionUID = 3578989117900232130L;

	public CidadeNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public CidadeNaoEncontradoException(Long id) {
		this(String.format("Cidade com id:%d não encontrada.", id));
	}

}
