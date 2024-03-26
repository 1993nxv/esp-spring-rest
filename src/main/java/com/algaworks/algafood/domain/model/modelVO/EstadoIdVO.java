package com.algaworks.algafood.domain.model.modelVO;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoIdVO {
	
	@NotNull
	private Long id;
}
