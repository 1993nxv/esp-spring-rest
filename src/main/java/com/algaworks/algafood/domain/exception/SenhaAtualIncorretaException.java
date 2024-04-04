package com.algaworks.algafood.domain.exception;

public class SenhaAtualIncorretaException extends NegocioException {

	private static final long serialVersionUID = 1906869584815875546L;

	public SenhaAtualIncorretaException(String mensagem) {
		super(mensagem);
	}

}
