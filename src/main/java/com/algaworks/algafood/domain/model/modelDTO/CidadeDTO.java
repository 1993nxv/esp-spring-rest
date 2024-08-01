package com.algaworks.algafood.domain.model.modelDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Getter
@Setter
public class CidadeDTO {
	
	@ApiModelProperty(value = "ID da cidade", example = "1")
	private Long id;
	@ApiModelProperty(example = "São Paulo")
	private String nome;

	private EstadoDTO estado;

}
