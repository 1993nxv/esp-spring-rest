package com.algaworks.algafood.domain.model.modelDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Representa uma cozinha")
@Getter
@Setter
public class CozinhaDTO {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Brasiliera")
	private String nome;
}
