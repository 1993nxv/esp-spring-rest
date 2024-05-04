package com.algaworks.algafood.domain.model.modelVO;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteIdVO {
	
	@NotBlank
	private Long id;
	
}
