package com.algaworks.algafood.domain.model.modelVO;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaVO {

	@NotBlank
	private String nome;
	
}
