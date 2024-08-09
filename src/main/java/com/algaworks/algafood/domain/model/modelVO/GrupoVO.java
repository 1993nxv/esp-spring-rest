package com.algaworks.algafood.domain.model.modelVO;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Representa um grupo")
@Getter
@Setter
public class GrupoVO {
	
	@ApiModelProperty(example = "Gerente")
	@NotBlank
	private String nome;
}
