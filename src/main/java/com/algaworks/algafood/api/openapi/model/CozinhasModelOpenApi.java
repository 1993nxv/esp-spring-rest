package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import com.algaworks.algafood.domain.model.modelDTO.CozinhaDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("CozinhasModel")
public class CozinhasModelOpenApi {
	
	
	private List<CozinhaDTO> content;
	
	@ApiModelProperty(example = "10", value = "Quantidade de regitros por página")
	private Long size;
	
	@ApiModelProperty(example = "50", value = "Total de registros")
	private Long totalElements;
	
	@ApiModelProperty(example = "5", value = "Total de páginas")
	private Long totalPages;
	
	@ApiModelProperty(example = "0", value = "Número da página, começa em 0")
	private Long number;
	
}
