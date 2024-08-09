package com.algaworks.algafood.domain.model.modelDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Representa um grupo")
@Getter
@Setter
public class GrupoDTO {
	
	@ApiModelProperty(example = "1")
	private Long id;
	@ApiModelProperty(value = "Nome do grupo", example = "Gerente")
	private String nome;
}
