package com.algaworks.algafood.domain.model.modelVO;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioComSenhaVO extends UsuarioVO {

	@NotBlank
	private String senha;
	
}
