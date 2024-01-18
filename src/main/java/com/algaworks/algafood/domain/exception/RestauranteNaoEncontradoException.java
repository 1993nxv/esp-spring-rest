package com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends NegocioException {

	private static final long serialVersionUID = -3587009515313174352L;

	public RestauranteNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public RestauranteNaoEncontradoException(Long id) {
		this(String.format("Restaurante com id:%d não encontrado.", id));
	}

}
