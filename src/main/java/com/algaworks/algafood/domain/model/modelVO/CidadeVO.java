package com.algaworks.algafood.domain.model.modelVO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Getter
@Setter
public class CidadeVO {
	
	@ApiModelProperty(example = "São Paulo")
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "1")
	@Valid
	@NotNull
	private EstadoIdVO estado;
	
}
