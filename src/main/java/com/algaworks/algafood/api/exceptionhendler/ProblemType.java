package com.algaworks.algafood.api.exceptionhendler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	CORPO_INVALIDO("/corpo-invalido", "Corpo enviado inválido"),
	ENTIDADE_NAO_ENCONTRADA("/enditade-nao-encontrada", "Entidade não encontrada"),
	ENTIDADE_EM_USO("/enditade-em-uso", "Entidade em uso"),
	NEGOCIO_EXCEPTION("/negocio-exception", "Negócio exception");
	
	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		this.uri = "https://api.algafoods.local" + path;
		this.title = title;
	}
}
