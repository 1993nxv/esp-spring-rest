package com.algaworks.algafood.domain.model.modelVO;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeIdVO {
	
	@NotNull
	private Long id;

}
