package com.algaworks.algafood.domain.exception;

public class EstadoNaoEncontradoException extends NegocioException {

	private static final long serialVersionUID = -5616324017094646008L;

	public EstadoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public EstadoNaoEncontradoException(Long id) {
		this(String.format("Estado com id:%d não encontrado.", id));
	}

}
