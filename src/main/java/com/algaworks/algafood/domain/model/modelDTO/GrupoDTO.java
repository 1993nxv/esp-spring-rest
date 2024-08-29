package com.algaworks.algafood.domain.model.modelDTO;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "grupos")
@ApiModel(description = "Representa um grupo")
@Getter
@Setter
public class GrupoDTO extends RepresentationModel<GrupoDTO> {
	
	@ApiModelProperty(example = "1")
	private Long id;
	@ApiModelProperty(value = "Nome do grupo", example = "Gerente")
	private String nome;
}
