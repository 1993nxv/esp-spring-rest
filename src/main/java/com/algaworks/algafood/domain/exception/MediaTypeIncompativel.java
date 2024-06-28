package com.algaworks.algafood.domain.exception;

public class MediaTypeIncompativel extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = -2938707312117863550L;

	public MediaTypeIncompativel(String mensagem, String mediaTypesAceitas) {
		super(mensagem + " - Considere usar " + mediaTypesAceitas);
	}
}
