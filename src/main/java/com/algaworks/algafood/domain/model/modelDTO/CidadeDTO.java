package com.algaworks.algafood.domain.model.modelDTO;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Representa uma cidade")
@Getter
@Setter
public class CidadeDTO extends RepresentationModel<CidadeDTO>{
	
	@ApiModelProperty(value = "ID da cidade", example = "1")
	private Long id;
	@ApiModelProperty(example = "São Paulo")
	private String nome;

	private EstadoDTO estado;

}
