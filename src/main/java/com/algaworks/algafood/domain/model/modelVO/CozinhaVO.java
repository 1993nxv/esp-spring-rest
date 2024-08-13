package com.algaworks.algafood.domain.model.modelVO;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Representa uma cozinha")
@Getter
@Setter
public class CozinhaVO {
	
	@ApiModelProperty(example = "Brasileira")
	@NotBlank
	private String nome;
	
}
