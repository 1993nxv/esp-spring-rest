package com.algaworks.algafood.domain.exception;

public class ReportException extends RuntimeException {

	private static final long serialVersionUID = 5504849586154847968L;

	public ReportException(String mensagem) {
		super(mensagem);
	}
}
