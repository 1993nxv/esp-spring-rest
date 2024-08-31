package com.algaworks.algafood.domain.model.modelDTO;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoDTO extends RepresentationModel<FotoProdutoDTO>{

	private Long id;
	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;
	
}
