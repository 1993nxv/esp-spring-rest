package com.algaworks.algafood.domain.model.modelDTO;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoDTO extends RepresentationModel<PermissaoDTO>{
	
	private Long id;
	private String nome;
	private String descricao;
}
