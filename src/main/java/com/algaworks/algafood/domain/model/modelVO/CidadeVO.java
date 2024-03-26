package com.algaworks.algafood.domain.model.modelVO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeVO {
	
	@NotBlank
	private String nome;
		
	@Valid
	@NotNull
	private EstadoIdVo estado;
	
}
