package com.algaworks.algafood.domain.model.modelDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoDTO {

	private Long id;
	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;
	
}
